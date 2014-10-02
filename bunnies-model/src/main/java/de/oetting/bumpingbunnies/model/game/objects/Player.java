package de.oetting.bumpingbunnies.model.game.objects;


public class Player implements GameObject {

	enum HorizontalMovementStatus {
		MOVING_LEFT, MOVING_RIGHT, NOT_MOVING_HORIZONTAL
	}

	private final int speedFaktor;

	private final int halfWidth;
	private final int halfHeight;
	private final int id;
	private final String name;

	private PlayerState state;
	private Player simulatedObject;

	private Rect rect;
	private HorizontalMovementStatus horizontalMovementStatus;
	private int color;
	private final Opponent opponent;

	public Player(int id, String name, int speedFaktor, Opponent opponent) {
		this.name = name;
		this.speedFaktor = speedFaktor;
		this.opponent = opponent;
		this.rect = new Rect();
		this.state = new PlayerState(id);
		this.id = id;
		this.halfHeight = ModelConstants.PLAYER_HEIGHT / 2;
		this.halfWidth = ModelConstants.PLAYER_WIDTH / 2;
		this.horizontalMovementStatus = HorizontalMovementStatus.NOT_MOVING_HORIZONTAL;
		calculateRect();
	}

	public Player(Player simulatedObject, int id, String name, int speedFaktor,
			Opponent opponent) {
		this(id, name, speedFaktor, opponent);
		this.simulatedObject = simulatedObject;
	}

	public void calculateRect() {
		long centerX = this.state.getCenterX();
		long centerY = this.state.getCenterY();
		this.rect.setMinX(centerX - this.halfWidth);
		this.rect.setMaxX(centerX + this.halfWidth);
		this.rect.setMinY(centerY - this.halfHeight);
		this.rect.setMaxY(centerY + this.halfHeight);
	}

	public long getCenterX() {
		return this.state.getCenterX();
	}

	public void setCenterX(long centerX) {
		this.state.setCenterX(centerX);
		calculateRect();
	}

	public long getCenterY() {
		return this.state.getCenterY();
	}

	public void setCenterY(long centerY) {
		this.state.setCenterY(centerY);
		calculateRect();
	}

	public void setMovementX(int movementX) {
		this.state.setMovementX(movementX * this.speedFaktor);
	}

	public void setExactMovementX(int movementX) {
		this.state.setMovementX(movementX);
	}

	public void increaseYMovement(int delta) {
		this.state.setMovementY(this.state.getMovementY() + delta);
	}

	public void setMovementY(int movementY) {
		this.state.setMovementY(movementY * this.speedFaktor);
	}

	public void setExactMovementY(int movementY) {
		this.state.setMovementY(movementY);
	}

	public int getAccelerationX() {
		return this.state.getAccelerationX();
	}

	public void setAccelerationX(int accelerationX) {
		this.state.setAccelerationX((int) (accelerationX * Math.pow(
				this.speedFaktor, 2)));
	}

	public int getAccelerationY() {
		return this.state.getAccelerationY();
	}

	public void setAccelerationY(int accelerationY) {
		this.state.setAccelerationY((int) (accelerationY * Math.pow(
				this.speedFaktor, 2)));
	}

	@Override
	public long maxX() {
		return this.rect.getMaxX();
	}

	@Override
	public long maxY() {
		return this.rect.getMaxY();
	}

	@Override
	public long minX() {
		return this.rect.getMinX();
	}

	@Override
	public long minY() {
		return this.rect.getMinY();
	}

	public void moveNextStep() {
		moveNextStepX();
		moveNextStepY();
		calculateRect();
	}

	public void calculateNextSpeed() {
		this.state.setMovementY(calculateNewMovementSpeedY());

		int newMovementSpeedX = calculateNewMovementSpeedX();
		this.state.setMovementX(newMovementSpeedX);
	}

	public int calculateNewMovementSpeedY() {
		return this.state.getMovementY() + this.state.getAccelerationY();
	}

	private int calculateNewMovementSpeedX() {
		int newMovementSpeedX = this.state.getMovementX()
				+ this.state.getAccelerationX();
		if (Math.abs(newMovementSpeedX) > ModelConstants.MOVEMENT
				* this.speedFaktor) {
			return (int) (Math.signum(newMovementSpeedX)
					* ModelConstants.MOVEMENT * this.speedFaktor);
		} else {
			return newMovementSpeedX;
		}
	}

	public int movementX() {
		return this.state.getMovementX();
	}

	public int movementY() {
		return this.state.getMovementY();
	}

	/**
	 * Careful: each new simulation will overwrite previous results
	 */
	public GameObject simulateNextStep() {
		resetSimulatedObject();
		this.simulatedObject.moveNextStep();
		return this.simulatedObject;
	}

	public void resetSimulatedObject() {
		this.simulatedObject.setCenterX(this.state.getCenterX());
		this.simulatedObject.setExactMovementX(this.state.getMovementX());
		this.simulatedObject.setCenterY(this.state.getCenterY());
		this.simulatedObject.setExactMovementY(this.state.getMovementY());
	}

	public int id() {
		return this.id;
	}

	public PlayerState getState() {
		return this.state;
	}

	@Override
	public int getColor() {
		return this.color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public long centerX() {
		return this.state.getCenterX();
	}

	public long centerY() {
		return this.state.getCenterY();
	}

	@Override
	public int accelerationOnThisGround() {
		return 0;
	}

	@Override
	public void interactWithPlayerOnTop(Player p) {
	}

	public GameObject simulateNextStepX() {
		resetSimulatedObject();
		this.simulatedObject.moveNextStepX();
		this.simulatedObject.calculateRect();
		return this.simulatedObject;
	}

	public GameObject simulateNextStepY() {
		resetSimulatedObject();
		this.simulatedObject.moveNextStepY();
		this.simulatedObject.calculateRect();
		return this.simulatedObject;
	}

	private void moveNextStepX() {
		this.state.setCenterX(this.state.getCenterX()
				+ this.state.getMovementX());
	}

	private void moveNextStepY() {
		this.state.setCenterY(this.state.getCenterY()
				+ this.state.getMovementY());
	}

	public boolean isFacingLeft() {
		return this.state.isFacingLeft();
	}

	public void setFacingLeft(boolean facingLeft) {
		this.state.setFacingLeft(facingLeft);
	}

	public String getName() {
		return this.name;
	}

	public void applyStateTo(Player player) {
		this.state.copyContentTo(player.state);
	}

	public void applyState(PlayerState state) {
		state.copyContentTo(this.state);
	}

	public int getScore() {
		return this.state.getScore();
	}

	public void setScore(int score) {
		this.state.setScore(score);
	}

	public void increaseScore(int i) {
		this.state.setScore(this.state.getScore() + i);
	}

	public void setDead(boolean b) {
		this.state.setDead(b);
	}

	public boolean isDead() {
		return this.state.isDead();
	}

	public int getSpeedFaktor() {
		return this.speedFaktor;
	}

	public boolean isTryingToRemoveHorizontalMovement() {
		return HorizontalMovementStatus.NOT_MOVING_HORIZONTAL
				.equals(this.horizontalMovementStatus);
	}

	public void setNotMoving() {
		this.horizontalMovementStatus = HorizontalMovementStatus.NOT_MOVING_HORIZONTAL;
	}

	public void setMovingRight() {
		this.horizontalMovementStatus = HorizontalMovementStatus.MOVING_RIGHT;
		this.state.setFacingLeft(false);
	}

	public void setMovingLeft() {
		this.horizontalMovementStatus = HorizontalMovementStatus.MOVING_LEFT;
		this.state.setFacingLeft(true);
	}

	public boolean isMovingLeft() {
		return HorizontalMovementStatus.MOVING_LEFT
				.equals(this.horizontalMovementStatus);
	}

	public boolean isJumpingButtonPressed() {
		return this.state.isJumpingButtonPressed();
	}

	public boolean belongsToOwner(Opponent opponent) {
		return opponent.equals(this.opponent);
	}

	public Opponent getOpponent() {
		return this.opponent;
	}

	@Override
	public String toString() {
		return "Player [speedFaktor=" + speedFaktor + ", halfWidth="
				+ halfWidth + ", halfHeight=" + halfHeight + ", id=" + id
				+ ", name=" + name + ", state=" + state + ", simulatedObject="
				+ simulatedObject + ", rect=" + rect
				+ ", horizontalMovementStatus=" + horizontalMovementStatus
				+ ", color=" + color + ", opponent=" + opponent + "]";
	}

}