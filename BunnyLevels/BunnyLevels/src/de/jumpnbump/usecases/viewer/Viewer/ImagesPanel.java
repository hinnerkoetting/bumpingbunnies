package de.jumpnbump.usecases.viewer.Viewer;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ImagesPanel extends JPanel {

	public void build() {
		Properties props = loadProperties();
		for (Entry<Object, Object> prop : props.entrySet()) {
			addImage(prop);
		}
	}

	private void addImage(Entry<Object, Object> prop) {
		String value = (String) prop.getValue();
		Image image = readImage(value);
		JLabel picLabel = new ImagePanel(new ImageIcon(image), (String) prop.getKey());
		add(picLabel);
	}

	private Image readImage(String resource) {
		try {
			BufferedImage bufImg = ImageIO.read(getClass().getResourceAsStream("/" + resource));
			return bufImg.getScaledInstance(100, 100, 0);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Properties loadProperties() {
		Properties prop = new Properties();
		try {
			prop.load(getClass().getResourceAsStream("/images.properties"));
			return prop;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
