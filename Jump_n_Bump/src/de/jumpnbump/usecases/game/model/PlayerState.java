package de.jumpnbump.usecases.game.model;

public class PlayerState implements GameObjectState<PlayerState> {

	private int centerX;
	private int centerY;
	private int movementX;
	private int movementY;
	private int accelerationX;
	private int accelerationY;
	private int score;
	private int color;
	private int id;

	public PlayerState(int id) {
		this.id = id;
	}

	public int getCenterX() {
		return this.centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
	}

	public int getCenterY() {
		return this.centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
	}

	public int getMovementX() {
		return this.movementX;
	}

	public void setMovementX(int movementX) {
		this.movementX = movementX;
	}

	public int getMovementY() {
		return this.movementY;
	}

	public void setMovementY(int movementY) {
		this.movementY = movementY;
	}

	public int getAccelerationX() {
		return this.accelerationX;
	}

	public void setAccelerationX(int accelerationX) {
		this.accelerationX = accelerationX;
	}

	public int getAccelerationY() {
		return this.accelerationY;
	}

	public void setAccelerationY(int accelerationY) {
		this.accelerationY = accelerationY;
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
		other.accelerationX = this.accelerationX;
		other.accelerationY = this.accelerationY;
		other.centerX = this.centerX;
		other.centerY = this.centerY;
		other.color = this.color;
		other.movementX = this.movementX;
		other.movementY = this.movementY;
		other.score = this.score;
	}

	@Override
	public String toString() {
		return "PlayerState [centerX=" + this.centerX + ", centerY="
				+ this.centerY + ", movementX=" + this.movementX
				+ ", movementY=" + this.movementY + ", accelerationX="
				+ this.accelerationX + ", accelerationY=" + this.accelerationY
				+ "]";
	}

}
