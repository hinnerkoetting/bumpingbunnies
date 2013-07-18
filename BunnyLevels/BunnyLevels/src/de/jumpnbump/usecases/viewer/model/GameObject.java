package de.jumpnbump.usecases.viewer.model;

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

	void setCenterX(int gameX);

	void setCenterY(int gameY);

}
