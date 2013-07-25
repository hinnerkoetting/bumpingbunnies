package de.jumpnbump.usecases.viewer.model;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageReader {

	public static BufferedImage readImage(String resource) {
		try {
			BufferedImage bufImg = ImageIO.read(resource.getClass().getResourceAsStream("/" + resource));
			return bufImg;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
