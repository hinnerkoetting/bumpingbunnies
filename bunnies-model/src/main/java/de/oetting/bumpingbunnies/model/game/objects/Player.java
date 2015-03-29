package de.oetting.bumpingbunnies.model.game.objects;

public class Player implements GameObject {

	private final int speedFaktor;

	private final int halfWidth;
	private final int halfHeight;
	private final int id;
	private final String name;

	private PlayerState state;
	private PlayerSimulation simulatedObject;

	private int color;
	private final ConnectionIdentifier opponent;

	private int score;
	private boolean dead;

	private int accelerationX;
	private int accelerationY;
	private boolean isInWater;

	public Player(int id, String name, int speedFaktor, ConnectionIdentifier opponent) {
		this.name = name;
		this.speedFaktor = speedFaktor;
		this.opponent = opponent;
		this.state = new PlayerState(id);
		this.id = id;
		this.halfHeight = ModelConstants.BUNNY_HEIGHT / 2;
		this.halfWidth = ModelConstants.BUNNY_WIDTH / 2;
		simulatedObject = new PlayerSimulation(new PlayerState(id), halfWidth, halfHeight);
	}

	public Player(Player player) {
		super();
		this.speedFaktor = player.speedFaktor;
		this.halfWidth = player.halfWidth;
		this.halfHeight = player.halfHeight;
		this.id = player.id;
		this.name = player.name;
		this.state = player.state.clone();
		this.color = player.color;
		this.opponent = player.opponent.clone();
		simulatedObject = new PlayerSimulation(new PlayerState(id), halfWidth, halfHeight);
	}

	public long getCenterX() {
		return this.state.getCenterX();
	}

	public void setCenterX(long centerX) {
		this.state.setCenterX(centerX);
	}

	public long getCenterY() {
		return this.state.getCenterY();
	}

	public void setCenterY(long centerY) {
		this.state.setCenterY(centerY);
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
		return accelerationX;
	}

	public void setAccelerationX(int accelerationX) {
		this.accelerationX = (int) (accelerationX * Math.pow(this.speedFaktor, 2));
	}

	public int getAccelerationY() {
		return accelerationY;
	}

	public void setAccelerationY(int accelerationY) {
		this.accelerationY = (int) (accelerationY * Math.pow(this.speedFaktor, 2));
	}

	@Override
	public long maxX() {
		long centerX = this.state.getCenterX();
		return centerX + this.halfWidth;
	}

	@Override
	public long maxY() {
		long centerY = this.state.getCenterY();
		return centerY + this.halfHeight;
	}

	@Override
	public long minX() {
		long centerX = this.state.getCenterX();
		return centerX - this.halfWidth;
	}

	@Override
	public long minY() {
		long centerY = this.state.getCenterY();
		return centerY - this.halfHeight;
	}

	public void moveNextStep() {
		state.moveNextStep();
	}

	public void calculateNextSpeed() {
		this.state.setMovementY(calculateNewMovementSpeedY());

		int newMovementSpeedX = calculateNewMovementSpeedX();
		this.state.setMovementX(newMovementSpeedX);
	}

	public int calculateNewMovementSpeedY() {
		return this.state.getMovementY() + accelerationY;
	}

	private int calculateNewMovementSpeedX() {
		int newMovementSpeedX = this.state.getMovementX() + accelerationX;
		if (Math.abs(newMovementSpeedX) > ModelConstants.MAX_X_MOVEMENT * this.speedFaktor) {
			return (int) (Math.signum(newMovementSpeedX) * ModelConstants.MAX_X_MOVEMENT * this.speedFaktor);
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
	public PlayerSimulation simulateNextStep() {
		return simulatedObject.moveNextStep(state);
	}

	public int id() {
		return this.id;
	}

	public PlayerState getState() {
		return this.state;
	}

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

	public GameObject simulateNextStepX() {
		return simulatedObject.moveNextStepX(state);
	}

	public GameObject simulateNextStepY() {
		return simulatedObject.moveNextStepY(state);
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

	public  void applyStateTo(Player player) {
		this.state.copyContentTo(player.state);
	}

	public  void applyState(PlayerState state) {
		state.copyContentTo(this.state);
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public void increaseScore(int increaseBy) {
		score += increaseBy;
	}

	public void setDead(boolean b) {
		this.dead = b;
	}

	public boolean isDead() {
		return dead;
	}

	public int getSpeedFaktor() {
		return this.speedFaktor;
	}

	public boolean isTryingToRemoveHorizontalMovement() {
		return HorizontalMovementState.NOT_MOVING_HORIZONTAL.equals(this.state.getHorizontalMovementStatus());
	}

	public void setNotMoving() {
		state.setHorizontalMovementStatus(HorizontalMovementState.NOT_MOVING_HORIZONTAL);
	}

	public void setMovingRight() {
		state.setHorizontalMovementStatus(HorizontalMovementState.MOVING_RIGHT);
		this.state.setFacingLeft(false);
	}

	public void setMovingLeft() {
		state.setHorizontalMovementStatus(HorizontalMovementState.MOVING_LEFT);
		this.state.setFacingLeft(true);
	}

	public boolean isMovingLeft() {
		return HorizontalMovementState.MOVING_LEFT.equals(this.state.getHorizontalMovementStatus());
	}

	public boolean isJumpingButtonPressed() {
		return this.state.isJumpingButtonPressed();
	}

	public ConnectionIdentifier getOpponent() {
		return this.opponent;
	}

	@Override
	public Player clone() {
		return new Player(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (id != other.id)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Player [speedFaktor=" + speedFaktor + ", halfWidth=" + halfWidth + ", halfHeight=" + halfHeight
				+ ", id=" + id + ", name=" + name + ", state=" + state + ", simulatedObject=" + simulatedObject
				+ ", color=" + color + ", opponent=" + opponent + ", score=" + score + ", dead=" + dead
				+ ", accelerationX=" + accelerationX + ", accelerationY=" + accelerationY + "]";
	}

	public void setJumping(boolean isJumping) {
		state.setJumpingButtonPressed(isJumping);
	}

	@Override
	public void setMinY(long newBottomY) {
		state.setCenterY(newBottomY + halfHeight);
	}

	@Override
	public void setMinX(long newLeft) {
		state.setCenterX(newLeft + halfWidth);
	}

	@Override
	public void setMaxX(long newRight) {
		state.setCenterX(newRight - halfWidth);
	}

	@Override
	public void setMaxY(long newTopY) {
		state.setCenterY(newTopY - halfHeight);
	}

	public boolean isInWater() {
		return isInWater;
	}

	public void setInWater(boolean isInWater) {
		this.isInWater = isInWater;
	}

	public void moveBackwards() {
		state.moveBackwards();
	}

}