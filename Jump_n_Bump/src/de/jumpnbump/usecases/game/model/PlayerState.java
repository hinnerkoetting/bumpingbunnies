package de.jumpnbump.usecases.game.model;

public class PlayerState implements GameObjectState<PlayerState> {

	private double centerX;
	private double centerY;
	private double movementX;
	private double movementY;
	private double accelerationX;
	private double accelerationY;
	private int score;
	private int color;
	private int id;

	public PlayerState(int id) {
		this.id = id;
	}

	public double getCenterX() {
		return this.centerX;
	}

	public void setCenterX(double centerX) {
		this.centerX = centerX;
	}

	public double getCenterY() {
		return this.centerY;
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;
	}

	public double getMovementX() {
		return this.movementX;
	}

	public void setMovementX(double movementX) {
		this.movementX = movementX;
	}

	public double getMovementY() {
		return this.movementY;
	}

	public void setMovementY(double movementY) {
		this.movementY = movementY;
	}

	public double getAccelerationX() {
		return this.accelerationX;
	}

	public void setAccelerationX(double accelerationX) {
		this.accelerationX = accelerationX;
	}

	public double getAccelerationY() {
		return this.accelerationY;
	}

	public void setAccelerationY(double accelerationY) {
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

}
