package de.jumpnbump.usecases.viewer.viewer;

import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import de.jumpnbump.usecases.viewer.MyCanvas;
import de.jumpnbump.usecases.viewer.model.ImageReader;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class ImagesPanel {

	private final MyCanvas canvas;
	private final EditorModel model;
	private JPanel panel;

	public ImagesPanel(MyCanvas canvas, EditorModel model) {
		this.canvas = canvas;
		this.model = model;
	}

	public JComponent build() {
		panel = new JPanel(new GridLayout(2, 0, 5, 5));
		Path directory = Paths.get("files");
		try {
			if (!Files.exists(directory))
				Files.createDirectory(directory);
			Files.newDirectoryStream(directory, (path) -> isImage(path)).forEach(path -> addImage(path));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		return createScrollPaneOver(panel);
	}

	private JComponent createScrollPaneOver(JPanel panel) {
		JScrollPane pane = new JScrollPane(panel);
		pane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		return pane;
	}

	private boolean isImage(Path path) {
		// TODO why is toString() necessary?
		return !Files.isDirectory(path) && path.getFileName().toString().endsWith(".png");
	}

	private void addImage(Path path) {
		BufferedImage image = readImage(path);
		Image scaledImage = scaleImage(image);
		final ImagePanel picLabel = new ImagePanel(image, new ImageIcon(scaledImage), path.getFileName().toString());
		panel.add(picLabel);
		picLabel.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent arg0) {
				model.storeState();
				List<GameObjectWithImage> selectedGameObjects = ImagesPanel.this.canvas.getSelectedGameObjects();
				selectedGameObjects.stream().forEach(
						go -> go.applyImage(new ImageWrapper(picLabel.getOriginal(), path.getFileName().toString())));
				ImagesPanel.this.canvas.repaint();
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
