package de.oetting.bumpingbunnies.model.game.objects;

public interface GameObject extends Rectangle {

	int accelerationOnThisGround();

	void setCenterX(long gameX);

	void setCenterY(long gameY);

	long getCenterX();

	long getCenterY();

	void setMinY(long newBottomY);

	void setMinX(long newLeft);

	int id();

	void setMaxX(long newRight);

	void setMaxY(long newTopY);

}
