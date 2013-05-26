package de.jumpnbump.usecases.game.model;


public interface GameObject {

	int id();

	double maxX();

	double maxY();

	double minX();

	double minY();

	double movementX();

	double movementY();

	void moveNextStepX();

	void moveNextStepY();

	GameObject simulateNextStepX();

	GameObject simulateNextStepY();

	void calculateNextSpeed();

	int getColor();

}
