package de.jumpnbump.usecases.viewer.viewer;

import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.model.ImageReader;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class ImagesPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private final MyCanvas canvas;

	public ImagesPanel(MyCanvas canvas) {
		super();
		this.canvas = canvas;
	}

	public void build() {
		Path directory = Paths.get("files");
		try {
			Files.newDirectoryStream(directory, (path) -> isImage(path)).forEach((path) -> addImage(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private boolean isImage(Path path) {
		// TODO why is toString() necessary?
		return !Files.isDirectory(path) && path.getFileName().toString().endsWith(".png");
	}

	private void addImage(Path path) {
		BufferedImage image = readImage(path);
		Image scaledImage = scaleImage(image);
		final ImagePanel picLabel = new ImagePanel(image, new ImageIcon(scaledImage), path.getFileName().toString());
		add(picLabel);
		picLabel.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				List<GameObjectWithImage> selectedGameObjects = ImagesPanel.this.canvas.getSelectedGameObjects();
				for (GameObjectWithImage go: selectedGameObjects) {
					ImageWrapper wrapper = new ImageWrapper(picLabel.getOriginal(), path.getFileName().toString());
					go.applyImage(wrapper);

					ImagesPanel.this.canvas.repaint();
				}
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

	private BufferedImage readImage(Path path) {
		return ImageReader.readImage(path);
	}

	private Image scaleImage(BufferedImage in) {
		return in.getScaledInstance(100, 100, 0);
	}

}
