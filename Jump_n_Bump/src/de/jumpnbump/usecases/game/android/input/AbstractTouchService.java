package de.jumpnbump.usecases.game.android.input;

import android.view.MotionEvent;
import de.jumpnbump.usecases.game.businesslogic.GameScreenSizeChangeListener;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.model.ModelConstants;

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

	protected int translateToGameXCoordinate(MotionEvent motionEvent) {
		return (int) (motionEvent.getX() / getWindowWidth() * ModelConstants.MAX_VALUE);
	}

	protected int translateToGameYCoordinate(MotionEvent motionEvent) {
		return (int) ((1 - motionEvent.getY() / getWindowHeight()) * ModelConstants.MAX_VALUE);
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

	public boolean isTouchRightToPlayer(MotionEvent motionEvent) {
		double touchX = translateToGameXCoordinate(motionEvent);
		return touchX > getMovedPlayer().maxX();
	}

	public boolean isTouchLeftToPlayer(MotionEvent motionEvent) {
		return motionEvent.getX() / this.windowWidth < 0.5;
		// double touchX = translateToGameXCoordinate(motionEvent);
		// return touchX < getMovedPlayer().minX();
	}

	public boolean clickOnUpperHalf(MotionEvent motionEvent) {
		return translateToGameYCoordinate(motionEvent) > 0.5 * ModelConstants.MAX_VALUE;
	}

}
