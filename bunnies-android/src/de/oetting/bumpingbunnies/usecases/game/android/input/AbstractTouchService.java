package de.oetting.bumpingbunnies.usecases.game.android.input;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.core.input.AbstractControlledMovement;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;

public abstract class AbstractTouchService extends AbstractControlledMovement {

	private final CoordinatesCalculation calculations;

	public AbstractTouchService(PlayerMovement playerMovement,
			CoordinatesCalculation calculations) {
		super(playerMovement);
		this.calculations = calculations;
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
