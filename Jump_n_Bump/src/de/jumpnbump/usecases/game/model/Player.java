package de.jumpnbump.usecases.game.model;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;

public class Player implements GameObject {

	private static final MyLog LOGGER = Logger.getLogger(Player.class);
	private PlayerState state;

	private Player simulatedObject;

	private PlayerRect rect;
	private int halfWidth;
	private int halfHeight;
	private int id;

	public Player(int id) {
		this.rect = new PlayerRect();
		this.state = new PlayerState(id);
		this.id = id;
		calculateRect();
		this.halfHeight = ModelConstants.PLAYER_HEIGHT / 2;
		this.halfWidth = ModelConstants.PLAYER_WIDTH / 2;
	}

	public Player(Player simulatedObject, int id) {
		this(id);
		this.simulatedObject = simulatedObject;
	}

	public void calculateRect() {
		int centerX = this.state.getCenterX();
		int centerY = this.state.getCenterY();
		this.rect.setMinX(centerX - this.halfWidth);
		this.rect.setMaxX(centerX + this.halfWidth);
		this.rect.setMinY(centerY - this.halfHeight);
		this.rect.setMaxY(centerY + this.halfHeight);
	}

	public int getCenterX() {
		return this.state.getCenterX();
	}

	public void setCenterX(int centerX) {
		this.state.setCenterX(centerX);
		calculateRect();
		LOGGER.debug("%s %d", "set", centerX);
	}

	public int getCenterY() {
		return this.state.getCenterY();
	}

	public void setCenterY(int centerY) {
		this.state.setCenterY(centerY);
		calculateRect();
	}

	public void setMovementX(int movementX) {
		this.state.setMovementX(movementX);
		LOGGER.verbose("setting movement x %f", this.state.getMovementX());
	}

	public void increaseYMovement(int delta) {
		this.state.setMovementY(this.state.getMovementY() + delta);
	}

	public void setMovementY(int movementY) {
		this.state.setMovementY(movementY);
	}

	public int getAccelerationX() {
		return this.state.getAccelerationX();
	}

	public void setAccelerationX(int accelerationX) {
		this.state.setAccelerationX(accelerationX);
	}

	public int getAccelerationY() {
		return this.state.getAccelerationY();
	}

	public void setAccelerationY(int accelerationY) {
		this.state.setAccelerationY(accelerationY);
	}

	@Override
	public int maxX() {
		return this.rect.getMaxX();
	}

	@Override
	public int maxY() {
		return this.rect.getMaxY();
	}

	@Override
	public int minX() {
		return this.rect.getMinX();
	}

	@Override
	public int minY() {
		return this.rect.getMinY();
	}

	public void moveNextStep() {
		moveNextStepX();
		moveNextStepY();
		calculateRect();
	}

	@Override
	public void calculateNextSpeed() {
		this.state.setMovementY(this.state.getMovementY()
				+ this.state.getAccelerationY());

		int newMovementSpeedX = calculateNewMovementSpeedX();
		this.state.setMovementX(newMovementSpeedX);
	}

	private int calculateNewMovementSpeedX() {
		int newMovementSpeedX = this.state.getMovementX()
				+ this.state.getAccelerationX();
		if (newMovementSpeedX > ModelConstants.MOVEMENT) {
			return ModelConstants.MOVEMENT;
		} else if (newMovementSpeedX < -ModelConstants.MOVEMENT) {
			return -ModelConstants.MOVEMENT;
		} else {
			return newMovementSpeedX;
		}
	}

	@Override
	public int movementX() {
		return this.state.getMovementX();
	}

	@Override
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
		this.simulatedObject.setMovementX(this.state.getMovementX());
		this.simulatedObject.setCenterY(this.state.getCenterY());
		this.simulatedObject.setMovementY(this.state.getMovementY());
	}

	@Override
	public int id() {
		return this.id;
	}

	public PlayerState getState() {
		return this.state;
	}

	@Override
	public int getColor() {
		return this.state.getColor();
	}

	@Override
	public int centerX() {
		return this.state.getCenterX();
	}

	@Override
	public int centerY() {
		return this.state.getCenterY();
	}

	@Override
	public int accelerationOnThisGround() {
		return 0;
	}

	@Override
	public void interactWithPlayerOnTop(Player p) {
		PlayerState state = p.getState();
		state.setScore(state.getScore() + 1);
		resetPosition(this, p);
	}

	private void resetPosition(Player playerUnder, Player playerOver) {
		PlayerState state = playerUnder.getState();
		if (state.getCenterX() > 0.75 * ModelConstants.MAX_VALUE) {
			state.setCenterX((int) (0.2 * ModelConstants.MAX_VALUE));
		} else {
			state.setCenterX((int) (0.8 * ModelConstants.MAX_VALUE));
		}
		state.setCenterY((int) (0.99 * ModelConstants.MAX_VALUE));

		PlayerState state2 = playerOver.getState();
		state2.setMovementY((int) (0.5 * ModelConstants.PLAYER_JUMP_SPEED));
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
}
