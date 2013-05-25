package de.jumpnbump.usecases.game.model;

import android.graphics.Paint;

public interface GameObject {

	double maxX();

	double maxY();

	double minX();

	double minY();

	double movementX();

	double movementY();

	void moveNextStepX();

	void moveNextStepY();

	void calculateNextSpeed();

	Paint getColor();

}
