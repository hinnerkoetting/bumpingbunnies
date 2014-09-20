package de.oetting.bumpingbunnies.android.input.touch;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.android.input.AbstractTouchService;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;

public class LeftRightTouchService extends AbstractTouchService {

	public LeftRightTouchService(PlayerMovement playerMovement,
			CoordinatesCalculation calculations) {
		super(playerMovement, calculations);
	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
			moveLeftOrRight(motionEvent);
		} else {
			reset();
		}
	}

	protected double relativePointerPositionX(MotionEvent motionEvent) {
		return -getMovedPlayer().getCenterX()
				+ translateToGameXCoordinate(motionEvent);
	}

	protected double relativePointerPositionY(MotionEvent motionEvent) {
		return translateToGameYCoordinate(motionEvent);
	}

	protected void moveLeftOrRight(MotionEvent motionEvent) {
		if (isTouchLeftToPlayer(motionEvent)) {
			moveLeft();
		} else if (isTouchRightToPlayer(motionEvent)) {
			moveRight();
		} else {
			reset();
		}
	}

}
