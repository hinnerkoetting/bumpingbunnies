package de.jumpnbump.usecases.viewer.Viewer;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class ImagePanel extends JLabel {

	private final String key;

	public ImagePanel(ImageIcon image, String key) {
		super(image);
		this.key = key;
	}

}
