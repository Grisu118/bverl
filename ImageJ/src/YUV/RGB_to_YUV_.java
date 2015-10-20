package YUV;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * Created by benjamin on 12.10.2015.
 */
public class RGB_to_YUV_ implements PlugInFilter {

    private static final int Y = 1;
    private static final int U = 2;
    private static final int V = 3;

    private static final int R = 0;
    private static final int G = 1;
    private static final int B = 2;

    @Override
    public int setup(String s, ImagePlus imagePlus) {
        return DOES_ALL;
    }

    @Override
    public void run(ImageProcessor imageProcessor) {
        //Y = 0.299*R + 0.587*G + 0.114*B
        //U = 0.492*(B - Y), V = 0.877*(R - Y)

        final int w = imageProcessor.getWidth();
        final int h = imageProcessor.getHeight();
        int[] yuv = new int[3];
        int[] rgb = new int[3];
        for (int v = 0; v < h; v++) {
            for (int u = 0; u < w; u++) {

                imageProcessor.getPixel(u, v, rgb);

                yuv[Y] = (int) (0.299*rgb[R] + 0.587*rgb[G] + 0.114*rgb[B]);
                yuv[U] = (int) (0.492*(rgb[B] - yuv[Y]));
                yuv[V] = (int) (0.877*(rgb[R] - yuv[Y]));

            }
        }
    }
}
