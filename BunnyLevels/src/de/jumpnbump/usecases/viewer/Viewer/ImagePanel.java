package de.jumpnbump.usecases.viewer.Viewer;

import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImagePanel extends JLabel {

	private static final long serialVersionUID = 1L;
	private final String key;
	private BufferedImage original;

	public ImagePanel(BufferedImage original, ImageIcon image, String key) {
		super(image);
		this.original = original;
		this.key = key;
	}

	public ImagePanel(String key, BufferedImage original) {
		super();
		this.key = key;
		this.original = original;
	}

	public BufferedImage getOriginal() {
		return this.original;
	}

	public void setOriginal(BufferedImage original) {
		this.original = original;
	}

	public String getKey() {
		return this.key;
	}

}
