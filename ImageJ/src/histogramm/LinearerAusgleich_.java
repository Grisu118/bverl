package histogramm;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

/**
 * Created by benjamin on 03.10.2015.
 */
public class LinearerAusgleich_ implements PlugInFilter {
    @Override
    public int setup(String s, ImagePlus imagePlus) {
        return DOES_8G;
    }

    @Override
    public void run(ImageProcessor imageProcessor) {
        int[] H = imageProcessor.getHistogram();
        int K = H.length;
        int HStrich = 0;

        int[] lookUpTable = new int[K];

        for (int i = 0; i < K; i++) {
            HStrich += H[i];

            lookUpTable[i] = HStrich * (K - 1) / (imageProcessor.getWidth() * imageProcessor.getHeight());
        }

        imageProcessor.applyTable(lookUpTable);
    }
}
