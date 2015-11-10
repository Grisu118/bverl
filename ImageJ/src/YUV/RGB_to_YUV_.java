package YUV;

import ij.ImagePlus;
import ij.gui.MessageDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * Created by benjamin on 12.10.2015.
 */
public class RGB_to_YUV_ implements PlugInFilter {
    private static final int Ri = 0;
    private static final int Gi = 1;
    private static final int Bi = 2;

    private ImagePlus imagePlus;

    @Override
    public int setup(String s, ImagePlus imagePlus) {
        this.imagePlus = imagePlus;
        return DOES_ALL;
    }

    @Override
    public void run(ImageProcessor imageProcessor) {
        //Y = 0.299*Ri+ 0.587*G + 0.114*B
        //U = 0.492*(B - Y), V = 0.877*(R - Y)

        final ImageProcessor nIp = imageProcessor.duplicate();
        final ImagePlus nImg = imagePlus.createImagePlus();
        nImg.setProcessor("RGB to YUV and back", nIp);

        final int w = imageProcessor.getWidth();
        final int h = imageProcessor.getHeight();
        int[] rgb = new int[3];
        double psnrR = 0, psnrG = 0, psnrB = 0;
        for (int v = 0; v < h; v++) {
            for (int u = 0; u < w; u++) {

                imageProcessor.getPixel(u, v, rgb);

                int Y = (int) Math.round(0.299*rgb[Ri] + 0.587*rgb[Gi] + 0.114*rgb[Bi]);
                int U = (int) Math.round(0.492*(rgb[Bi] - Y));
                int V = (int) Math.round(0.877*(rgb[Ri] - Y));

                Y = clamp(Y);

                int R = (int)Math.round(Y - 3.9457e-005*U + 1.1398*V);
                int G = (int)Math.round(Y - 0.39461*U - 0.5805*V);
                int B = (int)Math.round(Y + 2.032*U - 0.00048138*V);

                R = clamp(R);
                G = clamp(G);
                B = clamp(B);

                psnrR += (rgb[Ri] - R)*(rgb[Ri] - R);
                psnrG += (rgb[Gi] - G)*(rgb[Gi] - G);
                psnrB += (rgb[Bi] - B)*(rgb[Bi] - B);

                nIp.putPixel(u, v, (R << 16) | (G << 8) |  B);
            }
        }
        int size = w*h;
        psnrR = 20*Math.log10(255/Math.sqrt(psnrR/size));
        psnrB = 20*Math.log10(255/Math.sqrt(psnrG/size));
        psnrG = 20*Math.log10(255/Math.sqrt(psnrB/size));

        nImg.show();

        new MessageDialog(imagePlus.getWindow(), "PSNR", "Red: " + psnrR + ", Green:  " + psnrG + ", Blue:  " + psnrB);


    }

    private int clamp(int v) {
        if (v > 255) return 255;
        else if (v < 0) return 0;
        return v;
    }
}
