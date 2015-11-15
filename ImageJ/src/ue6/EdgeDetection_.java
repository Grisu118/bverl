package ue6;

import ij.*;
import ij.process.*;
import ij.plugin.filter.*;

/**
 * @author Christoph Stamm
 */
public class EdgeDetection_ implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		//return DOES_ALL;
		return DOES_8G;
	}

	public void run(ImageProcessor ip) {
		final int w = ip.getWidth();
		final int h = ip.getHeight();
		@SuppressWarnings("unused")
		final int len = 3;
		@SuppressWarnings("unused")
		final int runs = 100;
		final int[] hxx = { -1, 0, 1 };
		final int[] hxy = { 3, 10, 3 };
		final int weight = 32;
		final int flen = hxx.length;
		final int flen2 = flen/2;

		@SuppressWarnings("unused")
		StringBuilder sb = new StringBuilder();
		ByteProcessor ip2 = new ByteProcessor(w, h);
		ByteProcessor ip3 = new ByteProcessor(w, h);
		int[][] tx = new int[w][h];
		int[][] ty = new int[w][h];
		int i, sum, dx, dy;

		for (int v = 0; v < h; v++) {
			for (int u = flen2; u < w - flen2; u++) {
				sum = 0;
				for(i=0; i < flen; i++) {
					sum += ip.getPixel(u - flen2 + i, v)*hxx[i];
				}
				tx[u][v] = sum;

				sum = 0;
				for(i=0; i < flen; i++) {
					sum += ip.getPixel(u - flen2 + i, v)*hxy[i];
				}
				ty[u][v] = sum;
			}
		}

		int[][] val = new int[w][h];
		int[][] phi = new int[w][h];

		for (int u = 0; u < w; u++) {
			for (int v = flen2; v < h - flen2; v++) {
				sum = 0;
				for(i=0; i < flen; i++) {
					sum += tx[u][v - flen2 + i]*hxy[i];
				}
				dx = sum;

				sum = 0;
				for(i=0; i < flen; i++) {
					sum += ty[u][v - flen2 + i]*hxx[i];
				}
				dy = sum;

				// kantenst�rke
				val[u][v] = (int)(Math.sqrt(dx*dx + dy*dy)/weight);
				
				// binarisierung
				val[u][v] = (val[u][v] < 15) ? 0: 255;

				// kantenrichtung
				phi[u][v] = (int)((Math.atan2(dy, dx)/Math.PI + 1)*128);
				
				ip2.putPixel(u, v, val[u][v]);
				ip3.putPixel(u, v, phi[u][v]);
				//sb.append(tx[u][v]); sb.append(' ');
			}
		}

		//Editor ed = new Editor();
		//ed.setSize(350, 300);
		//ed.create("My Editor", sb.toString());

		ImagePlus imp2 = new ImagePlus("Kantenstärke", ip2);
		imp2.show();
		
		ImagePlus imp3 = new ImagePlus("Kantenrichtung", ip3);
		imp3.show();

	}
}
