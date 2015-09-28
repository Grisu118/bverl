// BVERI
// Übung 2.2

import ij.*;
import ij.process.*;
import ij.plugin.filter.*;
import ij.gui.*;
import ij.io.*;
import java.io.*;
import java.util.*;

/**
 * Huffman encoder
 * @author Christoph Stamm
 *
 */
public class Huffman_Enc implements PlugInFilter {
	ImagePlus imp;

	public int setup(String arg, ImagePlus imp) {
		this.imp = imp;
		return DOES_8G + NO_CHANGES;
	}

	public void run(ImageProcessor ip) {
		final int w = ip.getWidth();
		final int h = ip.getHeight();
		final int size = w*h;

		// Histogramm holen und Code-Tabelle erstellen
		int[] hist = ip.getHistogram();
		Leaf[] codes = new Leaf[hist.length];

		// Wahrscheinlichkeiten berechnen und Huffman-Codebaum erstellen
		Node root = createHuffmanTree(hist, codes, size);

		// Bild codieren
		BitSet data = encodeImage(ip, codes);

		// Bild speichern
		SaveDialog sd = new SaveDialog("Save image in HUF format", imp.getTitle(), ".huf");
		if (sd.getFileName() != null) {
			imp.startTiming();
			write(sd.getFileName(), sd.getDirectory(), ip, root, data);
		}
		
	}
	
	/**
	 * Build code tree
	 * @param hist histogram of input image
	 * @param codes code table
	 * @param size number of pixels
	 * @return root node of code tree
	 */
	private Node createHuffmanTree(int[] hist, Leaf[] codes, int size) {
		PriorityQueue<Node> pq = new PriorityQueue<Node>(hist.length);

		// Wahrscheinlichkeiten und Entropie berechnen und neue Blattknoten erzeugen (die Blattknoten sowohl in die Code-Tabelle als auch in die PQ einfügen)
		// ToDo

		// Mittlere Codelänge und Speicherbedarf abschätzen (Unter- und Obergrenze)
		// Verwenden Sie für die Ausgabe z.B. einen MessageDialog
		// ToDo

		// Mittlere Codelänge und Speicherbedarf berechnen und ausgeben
		// ToDo

		// Codebaum aufbauen: Verwenden Sie die pq, um die zwei jeweils kleinsten Nodes zu holen
		// ToDo

		// Wurzelknoten holen und rekursiv alle Codes erzeugen
		// ToDo

		return null;
	}

	/**
	 * Encode image
	 * @param ip
	 * @param codes code table
	 * @return encoded data
	 */
	private BitSet encodeImage(ImageProcessor ip, Leaf[] codes) {
		final int w = ip.getWidth();
		final int h = ip.getHeight();
		BitSet bs = new BitSet();
	
		// alle Pixel der Reihe nach codieren und im bs abspeichern
		// ToDo

		return bs;
	}
	
	/**
	 * Write file
	 * @param filename
	 * @param directory
	 * @param ip
	 * @param root root of code tree
	 * @param data encoded data
	 */
	private void write(String filename, String dir, ImageProcessor ip, Node root, BitSet data) {
		final int w = ip.getWidth();
		final int h = ip.getHeight();
		
		try{
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(dir + filename));
			
			// write Header
			// ToDo

			// write code tree
			// ToDo

			// write compressed data
			// ToDo

			// close file
			// ToDo
			
			IJ.showMessage("Huffman Encoder", "Image has been successfully saved.\n \n");
		} catch(Exception e){
			IJ.error("Huffman Encoder", e.getMessage());
		}
	}

}
