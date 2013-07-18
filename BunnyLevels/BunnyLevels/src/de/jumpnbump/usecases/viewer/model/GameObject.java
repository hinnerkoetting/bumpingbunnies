package de.jumpnbump.usecases.viewer.model;

import java.awt.image.BufferedImage;

public interface GameObject {

	int maxX();

	int maxY();

	int minX();

	int minY();

	int getColor();

	int id();

	int centerX();

	int centerY();

	int movementX();

	int movementY();

	void calculateNextSpeed();

	int accelerationOnThisGround();

	void setMaxY(int maxY);

	int getHeight();

	void setMinY(int newBottomY);

	void setCenterX(int gameX);

	void setCenterY(int gameY);

	void setMaxX(int newRight);

	void setMinX(int newLeft);

	void applyImage(BufferedImage image);

	public abstract BufferedImage getImage();

	public abstract boolean hasImage();

}
