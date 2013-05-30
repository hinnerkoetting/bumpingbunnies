package de.jumpnbump.usecases.game.model;

public interface GameObject {

	int id();

	double maxX();

	double maxY();

	double minX();

	double minY();

	double centerX();

	double centerY();

	double movementX();

	double movementY();

	void moveNextStepX();

	void moveNextStepY();

	GameObject simulateNextStepX();

	GameObject simulateNextStepY();

	void calculateNextSpeed();

	int getColor();

	public void setColor(int color);

}
