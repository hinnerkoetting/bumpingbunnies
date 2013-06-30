package de.oetting.bumpingbunnies.usecases.game.model;

public class PlayerState implements GameObjectState<PlayerState> {

	private MovingGameobjectState movementState;
	private int score;
	private int color;
	private int id;

	public PlayerState(int id) {
		this.id = id;
		this.movementState = new MovingGameobjectState();
	}

	public int getCenterX() {
		return this.movementState.centerX;
	}

	public void setCenterX(int centerX) {
		this.movementState.centerX = centerX;
	}

	public int getCenterY() {
		return this.movementState.centerY;
	}

	public void setCenterY(int centerY) {
		this.movementState.centerY = centerY;
	}

	public int getMovementX() {
		return this.movementState.movementX;
	}

	public void setMovementX(int movementX) {
		this.movementState.movementX = movementX;
	}

	public int getMovementY() {
		return this.movementState.movementY;
	}

	public void setMovementY(int movementY) {
		this.movementState.movementY = movementY;
	}

	public int getAccelerationX() {
		return this.movementState.accelerationX;
	}

	public void setAccelerationX(int accelerationX) {
		this.movementState.accelerationX = accelerationX;
	}

	public int getAccelerationY() {
		return this.movementState.accelerationY;
	}

	public void setAccelerationY(int accelerationY) {
		this.movementState.accelerationY = accelerationY;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getColor() {
		return this.color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public void copyContentTo(PlayerState other) {
		other.movementState.accelerationX = this.movementState.accelerationX;
		other.movementState.accelerationY = this.movementState.accelerationY;
		other.movementState.centerX = this.movementState.centerX;
		other.movementState.centerY = this.movementState.centerY;
		other.movementState.movementX = this.movementState.movementX;
		other.movementState.movementY = this.movementState.movementY;
		other.color = this.color;
		other.score = this.score;
	}

}
