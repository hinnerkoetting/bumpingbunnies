package de.oetting.bumpingbunnies.usecases.game.android.input.rememberPointer;

public class RememberPointerState {

	private boolean existsWaypoint;
	private double x;
	private double y;

	public void setNewWaypoint(double x, double y) {
		this.x = x;
		this.y = y;
		this.existsWaypoint = true;
	}

	public void waypointIsReached() {
		this.existsWaypoint = false;
	}

	public double getRememberedWaypointX() {
		return this.x;
	}

	public double getRememberedWaypointY() {
		return this.y;
	}

	public boolean isThereNewWaypoint() {
		return this.existsWaypoint;
	}
}
