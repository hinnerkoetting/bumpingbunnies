package de.jumpnbump.usecases.game.model;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;

public class Player implements GameObject {

	private static final MyLog LOGGER = Logger.getLogger(Player.class);
	private PlayerState state;

	private Player simulatedObject;

	private PlayerRect rect;

	public Player(int id) {
		this.rect = new PlayerRect();
		this.state = new PlayerState(id);
		calculateRect();
	}

	public Player(Player simulatedObject, int id) {
		this(id);
		this.simulatedObject = simulatedObject;
	}

	public void calculateRect() {
		int centerX = this.state.getCenterX();
		int centerY = this.state.getCenterY();
		this.rect.setMinX(centerX - ModelConstants.PLAYER_WIDTH / 2);
		this.rect.setMaxX(centerX + ModelConstants.PLAYER_WIDTH / 2);
		this.rect.setMinY(centerY - ModelConstants.PLAYER_HEIGHT / 2);
		this.rect.setMaxY(centerY + ModelConstants.PLAYER_HEIGHT / 2);
		LOGGER.verbose("Position MinX: %f - MaxX: %f - MinY: %f - MaxY: %f ",
				this.rect.getMinX(), this.rect.getMaxX(), this.rect.getMinY(),
				this.rect.getMaxY());
	}

	public int getCenterX() {
		return this.state.getCenterX();
	}

	public void setCenterX(int centerX) {
		this.state.setCenterX(centerX);
		calculateRect();
		LOGGER.debug("%s %f", "set", centerX);
	}

	public double getCenterY() {
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

	public double getAccelerationX() {
		return this.state.getAccelerationX();
	}

	public void setAccelerationX(int accelerationX) {
		this.state.setAccelerationX(accelerationX);
	}

	public double getAccelerationY() {
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
		this.state.setCenterX(this.state.getCenterX()
				+ this.state.getMovementX());
		this.state.setCenterY(this.state.getCenterY()
				+ this.state.getMovementY());
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

	public GameObject simulateNextStep() {
		resetSimulatedObject();
		this.simulatedObject.moveNextStep();
		return this.simulatedObject;
	}

	private void resetSimulatedObject() {
		this.simulatedObject.setCenterX(this.state.getCenterX());
		this.simulatedObject.setMovementX(this.state.getMovementX());
		this.simulatedObject.setCenterY(this.state.getCenterY());
		this.simulatedObject.setMovementY(this.state.getMovementY());
	}

	@Override
	public int id() {
		return this.state.getId();
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
	public void setColor(int color) {
		this.state.setColor(color);

	}

	@Override
	public int accelerationOnThisGround() {
		return 0;
	}

}
