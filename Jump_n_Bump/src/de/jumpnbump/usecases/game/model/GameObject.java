package de.jumpnbump.usecases.game.model;

public interface GameObject {

	int id();

	int maxX();

	int maxY();

	int minX();

	int minY();

	int centerX();

	int centerY();

	int movementX();

	int movementY();

	void moveNextStepX();

	void moveNextStepY();

	GameObject simulateNextStepX();

	GameObject simulateNextStepY();

	void calculateNextSpeed();

	int getColor();

	public void setColor(int color);

}
