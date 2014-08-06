package de.jumpnbump.usecases.viewer.model;

import java.awt.image.BufferedImage;

public class ImageWrapper {

	private final BufferedImage image;
	private final String name;

	public ImageWrapper(BufferedImage image, String name) {
		super();
		this.image = image;
		this.name = name;
	}

	public BufferedImage getImage() {
		return this.image;
	}

	public String getName() {
		return this.name;
	}

}
