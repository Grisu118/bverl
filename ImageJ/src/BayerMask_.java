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
						// hier die fehlenden 2 Farbkomponenten interpolieren
					} else {
						// g ist gegeben
						rgb[G] = ip.getPixel(u, v);
						// hier die fehlenden 2 Farbkomponenten interpolieren
					}
				} else {
					if (u%2 == 0) {
						// g ist gegeben
						rgb[G] = ip.getPixel(u, v);
						// hier die fehlenden 2 Farbkomponenten interpolieren
					} else {
						// r ist gegeben
						rgb[R] = ip.getPixel(u, v);
						// hier die fehlenden 2 Farbkomponenten interpolieren
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
