package de.jumpnbump.usecases.viewer.model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageReader {

	public static BufferedImage readImage(String resource) {
		try {
			BufferedImage bufImg = ImageIO.read(new File("files/" + resource));
			return bufImg;
		} catch (IOException e) {
			System.out.print("Error for resource " + resource);
			throw new RuntimeException(e);
		}
	}
}
