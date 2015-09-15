// BVERI
// Übung 1.3

import ij.*;
import ij.process.*;
import ij.gui.*;
import java.awt.*;
import ij.plugin.filter.*;

public class BayerMask_ implements PlugInFilter {
	static final int R = 0;
	static final int G = 1;
	static final int B = 2;
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_8G + NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		final int w = ip.getWidth();
		final int h = ip.getHeight();
		ImageProcessor ip2 = new ColorProcessor(w, h);	// neu zu erstellendes Farbbild
		int[] rgb = new int[3];
		
		// um die Aufgabe zu vereinfachen, führen wir keine Randbehandlung durch und lassen am Rand 2 Pixel schwarz
		for (int v=2; v < h - 2; v++) {
			for (int u=2; u < w - 2; u++) {
				if (v%2 == 0) {
					if (u%2 == 0) {
						// b ist gegeben
						rgb[B] = ip.getPixel(u, v);
						int r1 = ip.getPixel(u-1, v-1);
                        int r2 = ip.getPixel(u-1, v+1);
                        int r3 = ip.getPixel(u+1, v-1);
                        int r4 = ip.getPixel(u+1, v+1);
                        rgb[R] = (r1 + r2 + r3 + r4) / 4;
                        int g1 = ip.getPixel(u-1, v);
                        int g2 = ip.getPixel(u, v+1);
                        int g3 = ip.getPixel(u, v-1);
                        int g4 = ip.getPixel(u+1, v);
                        rgb[G] = (g1 + g2 + g3 + g4) / 4;

					} else {
						// g ist gegeben
						rgb[G] = ip.getPixel(u, v);
						int b1 = ip.getPixel(u+1, v);
                        int b2 = ip.getPixel(u-1, v);
                        rgb[B] = (b1 + b2) / 2;
                        int r1 = ip.getPixel(u, v+1);
                        int r2 = ip.getPixel(u, v-1);
                        rgb[R] = (r1 + r2) / 2;
					}
				} else {
					if (u%2 == 0) {
						// g ist gegeben
						rgb[G] = ip.getPixel(u, v);
                        int b1 = ip.getPixel(u, v+1);
                        int b2 = ip.getPixel(u, v-1);
                        rgb[B] = (b1 + b2) / 2;
                        int r1 = ip.getPixel(u+1, v);
                        int r2 = ip.getPixel(u-1, v);
                        rgb[R] = (r1 + r2) / 2;
					} else {
						// r ist gegeben
						rgb[R] = ip.getPixel(u, v);
                        int b1 = ip.getPixel(u-1, v-1);
                        int b2 = ip.getPixel(u-1, v+1);
                        int b3 = ip.getPixel(u+1, v-1);
                        int b4 = ip.getPixel(u+1, v+1);
                        rgb[B] = (b1 + b2 + b3 + b4) / 4;
                        int g1 = ip.getPixel(u-1, v);
                        int g2 = ip.getPixel(u, v+1);
                        int g3 = ip.getPixel(u, v-1);
                        int g4 = ip.getPixel(u+1, v);
                        rgb[G] = (g1 + g2 + g3 + g4) / 4;
					}
				}	
				// rgb-Komponenten des neuen Bildes setzen
				ip2.putPixel(u, v, rgb);
			}
		}
		ImagePlus imp2 = new ImagePlus("RGB", ip2);
		imp2.show();
	}

}
