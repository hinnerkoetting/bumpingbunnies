package de.jumpnbump.usecases.game.android.input;

import android.view.MotionEvent;
import de.jumpnbump.usecases.game.businesslogic.GameScreenSizeChangeListener;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

public abstract class AbstractTouchService extends AbstractControlledMovement
		implements GameScreenSizeChangeListener {

	private int windowWidth;
	private int windowHeight;

	public AbstractTouchService(PlayerMovementController playerMovement) {
		super(playerMovement);
	}

	@Override
	public void setNewSize(int width, int height) {
		this.windowHeight = height;
		this.windowWidth = width;
	}

	public abstract void onMotionEvent(MotionEvent motionEvent);

	protected double translateToGameXCoordinate(MotionEvent motionEvent) {
		return motionEvent.getX() / getWindowWidth();
	}

	protected double translateToGameYCoordinate(MotionEvent motionEvent) {
		return 1 - motionEvent.getY() / getWindowHeight();
	}

	public int getWindowWidth() {
		if (this.windowHeight == 0) {
			throw new IllegalStateException(
					"Widht not initialized. Forgot to register this as Listener to game screen");
		}
		return this.windowWidth;
	}

	public int getWindowHeight() {
		if (this.windowHeight == 0) {
			throw new IllegalStateException(
					"height not initialized. Forgot to register this as Listener to game screen");
		}
		return this.windowHeight;
	}
}
