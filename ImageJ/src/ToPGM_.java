import ij.IJ;
import ij.ImagePlus;
import ij.io.SaveDialog;
import ij.plugin.filter.PlugInFilter;
import ij.process.ImageProcessor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by benjamin on 28.09.2015.
 */
public class ToPGM_ implements PlugInFilter {
    @Override
    public int setup(String s, ImagePlus imagePlus) {
        return DOES_8G;
    }

    @Override
    public void run(ImageProcessor imageProcessor) {
        ImagePlus img = IJ.getImage();

        SaveDialog dialog = new SaveDialog("PGM 8Bit Gray (ASCII)" , img.getTitle(), ".pgm");

        String dir = dialog.getDirectory();
        String name = dialog.getFileName();

        if (name != null && dir != null) {
            IJ.showStatus("Writing ASCII PGM");
            File file = new File(dir + name);
            try {
                FileWriter writer = new FileWriter(file);
                writer.write("P2\n");
                int h = img.getHeight();
                int w = img.getWidth();
                writer.write(Integer.toString(w) + " " + Integer.toString(h) + "\n");
                writer.write("255\n");

                ImageProcessor proc = img.getProcessor();
                int[] line = new int[w];

                for (int i = 0; i < h; i++) {
                    proc.getRow(0, i, line, w);
                    for (int j = 0; j < w; j++) {
                        writer.write(Integer.toString(line[j]) + " ");
                    }
                    writer.write("\n");
                }
                writer.flush();
            } catch (IOException e) {
                IJ.error("Error writing file : " + e.toString());
            }
        }
        IJ.showStatus("");
    }
}
