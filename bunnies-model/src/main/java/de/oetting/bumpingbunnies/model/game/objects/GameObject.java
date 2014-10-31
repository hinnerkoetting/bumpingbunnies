package de.oetting.bumpingbunnies.model.game.objects;

public interface GameObject {

	long maxX();

	long maxY();

	long minX();

	long minY();

	int accelerationOnThisGround();

	void setCenterX(long gameX);

	void setCenterY(long gameY);

	void setMinY(long newBottomY);

	void setMinX(long newLeft);

	int id();

	void setMaxX(long newRight);

	void setMaxY(long newTopY);

}
