package de.oetting.bumpingbunnies.usecases.game.android.input.touch;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.AbstractTouchService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;

public class LeftRightTouchService extends AbstractTouchService {

	public LeftRightTouchService(PlayerMovementController playerMovement,
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

	@Override
	public void executeUserInput() {
		executeRememberedMovement();
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
			rememberMoveLeft();
		} else if (isTouchRightToPlayer(motionEvent)) {
			rememberMoveRight();
		} else {
			reset();
		}
	}

}
