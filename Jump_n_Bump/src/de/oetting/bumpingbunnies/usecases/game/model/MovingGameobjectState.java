package de.oetting.bumpingbunnies.usecases.game.model;

public class MovingGameobjectState {

	long centerX;
	long centerY;
	int movementX;
	int movementY;
	int accelerationX;
	int accelerationY;

	@Override
	public String toString() {
		return "MovingGameobjectState [centerX=" + this.centerX + ", centerY=" + this.centerY + ", movementX=" + this.movementX
				+ ", movementY="
				+ this.movementY + ", accelerationX=" + this.accelerationX + ", accelerationY=" + this.accelerationY + "]";
	}

}
