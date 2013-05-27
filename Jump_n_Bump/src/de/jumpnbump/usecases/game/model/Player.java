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
		double centerX = this.state.getCenterX();
		double centerY = this.state.getCenterY();
		this.rect.setMinX(centerX - ModelConstants.PLAYER_WIDTH / 2);
		this.rect.setMaxX(centerX + ModelConstants.PLAYER_WIDTH / 2);
		this.rect.setMinY(centerY - ModelConstants.PLAYER_HEIGHT / 2);
		this.rect.setMaxY(centerY + ModelConstants.PLAYER_HEIGHT / 2);
		LOGGER.verbose("Position MinX: %f - MaxX: %f - MinY: %f - MaxY: %f ",
				this.rect.getMinX(), this.rect.getMaxX(), this.rect.getMinY(),
				this.rect.getMaxY());
	}

	public double getCenterX() {
		return this.state.getCenterX();
	}

	public void setCenterX(double centerX) {
		this.state.setCenterX(centerX);
		calculateRect();
		LOGGER.debug("%s %f", "set", centerX);
	}

	public double getCenterY() {
		return this.state.getCenterY();
	}

	public void setCenterY(double centerY) {
		this.state.setCenterY(centerY);
		calculateRect();
	}

	public void setMovementX(double movementX) {
		this.state.setMovementX(movementX);
		LOGGER.verbose("setting movement x %f", this.state.getMovementX());
	}

	public void increaseYMovement(double delta) {
		this.state.setMovementY(this.state.getMovementY() + delta);
	}

	public void setMovementY(double movementY) {
		this.state.setMovementY(movementY);
	}

	public double getAccelerationX() {
		return this.state.getAccelerationX();
	}

	public void setAccelerationX(double accelerationX) {
		this.state.setAccelerationX(accelerationX);
	}

	public double getAccelerationY() {
		return this.state.getAccelerationY();
	}

	public void setAccelerationY(double accelerationY) {
		this.state.setAccelerationY(accelerationY);
	}

	@Override
	public double maxX() {
		return this.rect.getMaxX();
	}

	@Override
	public double maxY() {
		return this.rect.getMaxY();
	}

	@Override
	public double minX() {
		return this.rect.getMinX();
	}

	@Override
	public double minY() {
		return this.rect.getMinY();
	}

	@Override
	public void moveNextStepX() {
		this.state.setCenterX(this.state.getCenterX()
				+ this.state.getMovementX());
		if (this.state.getMovementX() > 0) {
			LOGGER.debug("moving x %f", this.state.getMovementX());
		}
		calculateRect();
	}

	@Override
	public void moveNextStepY() {
		this.state.setCenterY(this.state.getCenterY()
				+ this.state.getMovementY());
		calculateRect();
	}

	@Override
	public void calculateNextSpeed() {
		this.state.setMovementY(this.state.getMovementY()
				+ this.state.getAccelerationY());
		this.state.setMovementX(this.state.getMovementX()
				+ this.state.getAccelerationX());
	}

	@Override
	public double movementX() {
		return this.state.getMovementX();
	}

	@Override
	public double movementY() {
		return this.state.getMovementY();
	}

	@Override
	public GameObject simulateNextStepX() {
		this.simulatedObject.setCenterX(this.state.getCenterX());
		this.simulatedObject.setMovementX(this.state.getMovementX());
		this.simulatedObject.setCenterY(this.state.getCenterY());
		this.simulatedObject.setMovementY(this.state.getMovementY());
		this.simulatedObject.moveNextStepX();
		return this.simulatedObject;
	}

	@Override
	public GameObject simulateNextStepY() {
		this.simulatedObject.setCenterX(this.state.getCenterX());
		this.simulatedObject.setMovementX(this.state.getMovementX());
		this.simulatedObject.setCenterY(this.state.getCenterY());
		this.simulatedObject.setMovementY(this.state.getMovementY());
		this.simulatedObject.moveNextStepY();
		return this.simulatedObject;
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

}
