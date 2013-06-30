package de.oetting.bumpingbunnies.usecases.game.android.input;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameScreenSizeChangeListener;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;

public abstract class AbstractTouchService extends AbstractControlledMovement
		implements GameScreenSizeChangeListener {

	private int windowWidth;
	private int windowHeight;
	private final CoordinatesCalculation calculations;

	public AbstractTouchService(PlayerMovementController playerMovement,
			CoordinatesCalculation calculations) {
		super(playerMovement);
		this.calculations = calculations;
	}

	@Override
	public void setNewSize(int width, int height) {
		this.windowHeight = height;
		this.windowWidth = width;
	}

	public abstract void onMotionEvent(MotionEvent motionEvent);

	protected int translateToGameXCoordinate(MotionEvent motionEvent) {
		return this.calculations.getGameCoordinateX(motionEvent.getX());
		// (int) (motionEvent.getX() / getWindowWidth() *
		// ModelConstants.MAX_VALUE);
	}

	protected int translateToGameYCoordinate(MotionEvent motionEvent) {
		return this.calculations.getGameCoordinateY(motionEvent.getY());
	}

	public int getWindowWidth() {
		if (this.windowHeight == 0) {
			throw new IllegalStateException(
					"Width not initialized. Forgot to register this as Listener to game screen");
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
		return getMovedPlayer().getCenterX()
				- translateToGameXCoordinate(motionEvent) < 0;
	}

	public boolean isTouchLeftToPlayer(MotionEvent motionEvent) {
		return getMovedPlayer().getCenterX()
				- translateToGameXCoordinate(motionEvent) > 0;
		// double touchX = translateToGameXCoordinate(motionEvent);
		// return touchX < getMovedPlayer().minX();
	}

	public boolean clickOnUpperHalf(MotionEvent motionEvent) {
		return this.calculations.isClickOnUpperHalf(motionEvent);
	}

}
