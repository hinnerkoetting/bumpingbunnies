package de.jumpnbump.usecases.game.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;

public class Player implements GameObject {

	private static final MyLog LOGGER = Logger.getLogger(Player.class);
	private int centerX;
	private int centerY;
	private float movementX;
	private float movementY;

	private Rect drawRect;
	private Paint paint;

	public Player() {
		this.drawRect = new Rect();
		this.paint = new Paint();
		this.paint.setColor(ModelConstants.PLAYER_COLOR);
		this.centerX = 200;
		this.centerY = 200;
		calculateRect();
	}

	private void calculateRect() {
		this.drawRect.left = this.centerX - ModelConstants.PLAYER_WIDTH / 2;
		this.drawRect.right = this.centerX + ModelConstants.PLAYER_WIDTH / 2;
		this.drawRect.top = this.centerY - ModelConstants.PLAYER_HEIGHT / 2;
		this.drawRect.bottom = this.centerY + ModelConstants.PLAYER_HEIGHT / 2;
	}

	public int getCenterX() {
		return this.centerX;
	}

	public void setCenterX(int centerX) {
		this.centerX = centerX;
		calculateRect();
		LOGGER.debug("%s %d", "set", centerX);
	}

	public int getCenterY() {
		return this.centerY;
	}

	public void setCenterY(int centerY) {
		this.centerY = centerY;
		calculateRect();
	}

	public void setMovementX(float movementX) {
		this.movementX = movementX;
	}

	public void increaseYMovement(float delta) {
		this.movementY += delta;
	}

	public void increaseY(float movement) {
		this.centerY += movement;
	}

	public void setMovementY(float movementY) {
		this.movementY = movementY;
	}

	@Override
	public void draw(Canvas canvas) {
		LOGGER.debug("%s %d", "Draw", this.centerX);
		canvas.drawRect(this.drawRect, this.paint);
	}

	@Override
	public int maxX() {
		return this.drawRect.right;
	}

	@Override
	public int maxY() {
		return this.drawRect.bottom;
	}

	@Override
	public int minX() {
		return this.drawRect.left;
	}

	@Override
	public int minY() {
		return this.drawRect.top;
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
	public float movementX() {
		return this.movementX;
	}

	@Override
	public float movementY() {
		return this.movementY;
	}
}
