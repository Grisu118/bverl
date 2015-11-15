package YUV;

import ij.ImagePlus;
import ij.gui.MessageDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ByteProcessor;
import ij.process.ImageProcessor;
import utils.RGBUtils;

/**
 * Created by benjamin on 15.11.2015.
 */
public class RGB_to_Gray_ implements PlugInFilter {
    ImagePlus imagePlus;

    @Override
    public int setup(String s, ImagePlus imagePlus) {
        this.imagePlus = imagePlus;
        return DOES_ALL + NO_CHANGES;
    }

    private int[] InverseGammaLockUpTable() {
        double gamma= 1 / 2.4;
        double x0=0.00304;
        int K = 256;

        final double s = gamma / (x0 * (gamma - 1) + Math.pow(x0, 1 - gamma));
        final double d = 1 / (Math.pow(x0, gamma) * (gamma - 1) + 1) - 1;

        int[] lookUpTable = new int[K];

        for (int i = 0; i < lookUpTable.length; i++) {
            double x = (double) i / (K - 1);
            if (x <= s * x0) {
                lookUpTable[i] = (int) Math.round(x / s * (K - 1));
            } else {
                lookUpTable[i] = (int) Math.round(Math.pow((x + d) / (1 + d), 1 / gamma) * (K - 1));
            }
        }

        return lookUpTable;
    }

    @Override
    public void run(ImageProcessor imageProcessor) {
        final int w = imageProcessor.getWidth();
        final int h = imageProcessor.getHeight();
        int[] rgb = new int[3];
        int[] lockUpTable = InverseGammaLockUpTable();

        ImageProcessor ip2 = imageProcessor.duplicate();
        ImagePlus imp2 = imagePlus.createImagePlus();
        imp2.setProcessor("Gammakorrigiertes RGB", ip2);

        ImageProcessor ip3 = new ByteProcessor(w, h);
        ImagePlus imp3 = imagePlus.createImagePlus();
        imp3.setProcessor("Aufgabe B", ip3);

        ImageProcessor ip4 = new ByteProcessor(w, h);
        ImagePlus imp4 = imagePlus.createImagePlus();
        imp4.setProcessor("Aufgabe C, mit Linearisierung", ip4);
        double psnr = 0;

        for (int v = 0; v < h; v++) {
            for (int u = 0; u < w; u++) {

                imageProcessor.getPixel(u, v, rgb);

                int Y2 = RGBUtils.clamp((int)Math.round(0.309*rgb[0] + 0.609*rgb[1] + 0.082*rgb[2]));
                ip4.putPixel(u, v, Y2);

                rgb[0] = lockUpTable[rgb[0]];
                rgb[1] = lockUpTable[rgb[1]];
                rgb[2] = lockUpTable[rgb[2]];
                ip2.putPixel(u, v, rgb);

                int Y = RGBUtils.clamp((int) Math.round(0.299 * rgb[0] + 0.587 * rgb[1] + 0.114 * rgb[2]));

                ip3.putPixel(u, v, Y);

                psnr += (Y-Y2)*(Y-Y2);

            }
        }

        imp2.show();
        imp3.show();
        imp4.show();

        psnr = 20*Math.log10(255/Math.sqrt(psnr/(w*h)));
        new MessageDialog(imagePlus.getWindow(), "PSNR", "PSNR: " + psnr);

    }
}
