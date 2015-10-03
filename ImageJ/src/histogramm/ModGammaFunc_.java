package histogramm;

import ij.ImagePlus;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

import javax.swing.*;
import java.awt.*;

/**
 * Created by benjamin on 03.10.2015.
 */
public class ModGammaFunc_ implements PlugInFilter {
    @Override
    public int setup(String s, ImagePlus imagePlus) {
        return DOES_8G;
    }

    @Override
    public void run(ImageProcessor imageProcessor) {
        String itu = "ITU";
        String sRGB = "sRGB";

        double gamma;
        double x0;
        int K = 256;

        if (sRGB.equals(JOptionPane.showInputDialog(null, "Will You use ITU or sRGB Standart?", "sRGB or ITU?", JOptionPane.QUESTION_MESSAGE, null, new String[]{sRGB, itu}, sRGB))) {
            gamma = 1 / 2.4;
            x0 = 0.00304;
        } else {
            gamma = 1 / 2.222;
            x0 = 0.018;
        }

        final double s = gamma / (x0 * (gamma - 1) + Math.pow(x0, 1 - gamma));
        final double d = 1 / (Math.pow(x0, gamma) * (gamma - 1) + 1) - 1;

        int[] lookUpTable = new int[K];

        for (int i = 0; i < lookUpTable.length; i++) {
            double x = (double) i / (K - 1);
            if (x <= x0) {
                lookUpTable[i] = (int) Math.round(x * s * (K - 1));
            } else {
                lookUpTable[i] = (int) Math.round(((1 + d) * Math.pow(x, gamma) - d) * (K - 1));
            }
        }
        imageProcessor.applyTable(lookUpTable);
    }
}
