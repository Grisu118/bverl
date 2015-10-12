package YUV;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * Created by benjamin on 12.10.2015.
 */
public class YUVConversion implements PlugInFilter {
    @Override
    public int setup(String s, ImagePlus imagePlus) {
        return DOES_ALL;
    }

    @Override
    public void run(ImageProcessor imageProcessor) {
        //Y = 0.299*R + 0.587*G + 0.114*B
        //U = 0.492*(B – Y), V = 0.877*(R – Y)

        final int w = imageProcessor.getWidth();
        final int h = imageProcessor.getHeight();

        for (int v = 0; v < h; v++) {
            for (int u = 0; u < w; u++) {

            }
        }
    }
}
