import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class MyInverter_ implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		//return DOES_ALL;
		return DOES_8G;
	}

	public void run(ImageProcessor ip) {
		ip.invert();
		imp.updateAndDraw();
		IJ.wait(500);
		ip.invert();
		imp.updateAndDraw();

		final int w = ip.getWidth();
		final int h = ip.getHeight();
		int p;
		
		for (int v = 0; v < h; v++) {
			for (int u = 0; u < w; u++) {
				p = ip.getPixel(u, v);
				ip.putPixel(u, v, 255 - p);
			}
		}
	}

}
