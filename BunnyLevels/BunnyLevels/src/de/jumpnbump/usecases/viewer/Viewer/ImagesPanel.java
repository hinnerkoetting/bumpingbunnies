package de.jumpnbump.usecases.viewer.Viewer;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map.Entry;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.model.GameObject;
import de.jumpnbump.usecases.viewer.model.ImageReader;
import de.jumpnbump.usecases.viewer.model.ImageWrapper;

public class ImagesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final MyCanvas canvas;

	public ImagesPanel(MyCanvas canvas) {
		super();
		this.canvas = canvas;
	}

	public void build() {
		Properties props = loadProperties();
		for (Entry<Object, Object> prop : props.entrySet()) {
			addImage(prop);
		}
	}

	private void addImage(Entry<Object, Object> prop) {
		final String key = (String) prop.getKey();
		BufferedImage image = readImage(key);
		Image scaledImage = scaleImage(image);
		final ImagePanel picLabel = new ImagePanel(image, new ImageIcon(scaledImage), (String) prop.getKey());
		add(picLabel);
		picLabel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				GameObject selectedGameObject = ImagesPanel.this.canvas.getSelectedGameObject();
				ImageWrapper wrapper = new ImageWrapper(picLabel.getOriginal(), key);
				selectedGameObject.applyImage(wrapper);

				ImagesPanel.this.canvas.repaint();
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
	}

	private BufferedImage readImage(String resource) {
		return ImageReader.readImage(resource + ".png");
	}

	private Image scaleImage(BufferedImage in) {
		return in.getScaledInstance(100, 100, 0);
	}

	private Properties loadProperties() {
		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("files/config"));
			return prop;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
