package de.oetting.bumpingbunnies.model.game.objects;

/**
 * Contains all information about a player which has to be set over network.
 * 
 */
public class PlayerState implements GameObjectState<PlayerState> {

	private final int id;
	long centerX;
	long centerY;
	int movementX;
	int movementY;
	int accelerationX;
	int accelerationY;
	private int score;
	/**
	 * is bunny looking to the left (not necessarily moving left)
	 */
	private boolean facingLeft;
	private boolean jumpingButtonPressed;
	private boolean isDead;

	public PlayerState(int id) {
		this.id = id;
	}

	public PlayerState(PlayerState playerState) {
		this.id = playerState.id;
		this.centerX = playerState.centerX;
		this.centerY = playerState.centerY;
		this.movementX = playerState.movementX;
		this.movementY = playerState.movementY;
		this.accelerationX = playerState.accelerationX;
		this.accelerationY = playerState.accelerationY;
		this.score = playerState.score;
		this.facingLeft = playerState.facingLeft;
		this.jumpingButtonPressed = playerState.jumpingButtonPressed;
		this.isDead = playerState.isDead;
	}

	public long getCenterX() {
		return this.centerX;
	}

	public void setCenterX(long centerX) {
		this.centerX = centerX;
	}

	public long getCenterY() {
		return this.centerY;
	}

	public void setCenterY(long centerY) {
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

	public int getId() {
		return this.id;
	}

	public boolean isFacingLeft() {
		return this.facingLeft;
	}

	public void setFacingLeft(boolean facingLeft) {
		this.facingLeft = facingLeft;
	}

	public boolean isJumpingButtonPressed() {
		return this.jumpingButtonPressed;
	}

	public void setJumpingButtonPressed(boolean jumpingButtonIsPressed) {
		this.jumpingButtonPressed = jumpingButtonIsPressed;
	}

	public boolean isDead() {
		return this.isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	@Override
	public void copyContentTo(PlayerState other) {
		other.accelerationX = this.accelerationX;
		other.accelerationY = this.accelerationY;
		other.centerX = this.centerX;
		other.centerY = this.centerY;
		other.movementX = this.movementX;
		other.movementY = this.movementY;
		other.score = this.score;
		other.facingLeft = this.facingLeft;
		other.jumpingButtonPressed = this.jumpingButtonPressed;
		other.isDead = this.isDead;
	}

	@Override
	public String toString() {
		return "PlayerState [id=" + this.id + ", centerX=" + this.centerX + ", centerY=" + this.centerY + ", movementX=" + this.movementX + ", movementY="
				+ this.movementY + ", accelerationX=" + this.accelerationX + ", accelerationY=" + this.accelerationY + ", score=" + this.score
				+ ", facingLeft=" + this.facingLeft + ", jumpingButtonPressed=" + this.jumpingButtonPressed + ", isDead=" + this.isDead + "]";
	}

	@Override
	public PlayerState clone() {
		return new PlayerState(this);
	}
}
