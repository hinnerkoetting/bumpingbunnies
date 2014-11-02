package de.jumpnbump.usecases.viewer.model;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;

import javax.imageio.ImageIO;

public class ImageReader {

	public static BufferedImage readImage(Path path) {
		try {
			BufferedImage bufImg = ImageIO.read(path.toFile());
			return bufImg;
		} catch (IOException e) {
			System.out.print("Error for resource " + path);
			throw new RuntimeException(e);
		}
	}
}
