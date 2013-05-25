package de.jumpnbump.usecases.game.model;

import android.graphics.Canvas;

public interface GameObject {

	void draw(Canvas canvas);

	int maxX();

	int maxY();

	int minX();

	int minY();

	float movementX();

	float movementY();

	void moveNextStepX();

	void moveNextStepY();

}
