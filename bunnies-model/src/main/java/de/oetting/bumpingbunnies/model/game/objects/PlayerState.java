package de.oetting.bumpingbunnies.model.game.objects;

/**
 * Contains all information about a player which has to be sent over network.
 * 
 */
public class PlayerState implements GameObjectState<PlayerState> {

	public enum HorizontalMovementStatus {
		MOVING_LEFT, MOVING_RIGHT, NOT_MOVING_HORIZONTAL
	}

	private final int id;
	private long centerX;
	private long centerY;
	private int movementX;
	private int movementY;

	/**
	 * is bunny looking to the left (not necessarily moving left)
	 */
	private boolean facingLeft;
	private boolean jumpingButtonPressed;
	private HorizontalMovementStatus horizontalMovementStatus;

	public PlayerState(int id) {
		this.id = id;
		this.horizontalMovementStatus = HorizontalMovementStatus.NOT_MOVING_HORIZONTAL;
	}

	public PlayerState(PlayerState playerState) {
		this.id = playerState.id;
		this.centerX = playerState.centerX;
		this.centerY = playerState.centerY;
		this.movementX = playerState.movementX;
		this.movementY = playerState.movementY;
		this.facingLeft = playerState.facingLeft;
		this.jumpingButtonPressed = playerState.jumpingButtonPressed;
		this.horizontalMovementStatus = playerState.horizontalMovementStatus;
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

	@Override
	public void copyContentTo(PlayerState other) {
		other.centerX = this.centerX;
		other.centerY = this.centerY;
		other.movementX = this.movementX;
		other.movementY = this.movementY;
		other.facingLeft = this.facingLeft;
		other.jumpingButtonPressed = this.jumpingButtonPressed;
		other.horizontalMovementStatus = this.horizontalMovementStatus;
	}

	@Override
	public PlayerState clone() {
		return new PlayerState(this);
	}

	public void setHorizontalMovementStatus(HorizontalMovementStatus newStatus) {
		horizontalMovementStatus = newStatus;
	}

	public HorizontalMovementStatus getHorizontalMovementStatus() {
		return horizontalMovementStatus;
	}

	@Override
	public String toString() {
		return "PlayerState [id=" + id + ", centerX=" + centerX + ", centerY=" + centerY + ", movementX=" + movementX + ", movementY=" + movementY
				+ ", facingLeft=" + facingLeft + ", jumpingButtonPressed=" + jumpingButtonPressed + ", horizontalMovementStatus=" + horizontalMovementStatus
				+ "]";
	}

}
