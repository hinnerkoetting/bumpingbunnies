package de.jumpnbump.usecases.game.model;

import android.graphics.Paint;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;

public class Player implements GameObject {

	private static final MyLog LOGGER = Logger.getLogger(Player.class);
	private double centerX;
	private double centerY;
	private double movementX;
	private double movementY;
	private double accelerationX;
	private double accelerationY;

	private Paint paint;
	private PlayerRect rect;

	public Player() {
		this.rect = new PlayerRect();
		this.paint = new Paint();
		this.paint.setColor(ModelConstants.PLAYER_COLOR);
		this.centerX = 0.1;
		this.centerY = 0.5;
		calculateRect();
	}

	private void calculateRect() {
		this.rect.setMinX(this.centerX - ModelConstants.PLAYER_WIDTH / 2);
		this.rect.setMaxX(this.centerX + ModelConstants.PLAYER_WIDTH / 2);
		this.rect.setMinY(this.centerY - ModelConstants.PLAYER_HEIGHT / 2);
		this.rect.setMaxY(this.centerY + ModelConstants.PLAYER_HEIGHT / 2);
		LOGGER.verbose("Position MinX: %f - MaxX: %f - MinY: %f - MaxY: %f ",
				this.rect.getMinX(), this.rect.getMaxX(), this.rect.getMinY(),
				this.rect.getMaxY());
	}

	public double getCenterX() {
		return this.centerX;
	}

	public void setCenterX(double centerX) {
		this.centerX = centerX;
		calculateRect();
		LOGGER.debug("%s %f", "set", centerX);
	}

	public double getCenterY() {
		return this.centerY;
	}

	public void setCenterY(double centerY) {
		this.centerY = centerY;
		calculateRect();
	}

	public void setMovementX(double movementX) {
		this.movementX = movementX;
	}

	public void increaseYMovement(double delta) {
		this.movementY += delta;
	}

	public void increaseY(double movement) {
		this.centerY += movement;
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

	public void increaseAccelerationY(double delta) {
		this.accelerationY += delta;
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
		this.centerX += this.movementX;
		calculateRect();
	}

	@Override
	public void moveNextStepY() {
		this.centerY += this.movementY;
		calculateRect();
	}

	@Override
	public void calculateNextSpeed() {
		this.movementY += this.accelerationY;
	}

	@Override
	public double movementX() {
		return this.movementX;
	}

	@Override
	public double movementY() {
		return this.movementY;
	}

	public void setColor(int color) {
		this.paint.setColor(color);
	}

	@Override
	public Paint getColor() {
		return this.paint;
	}
}
