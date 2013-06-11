package de.jumpnbump.usecases.game.android.input.touch;

import android.view.MotionEvent;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.android.calculation.CoordinatesCalculation;
import de.jumpnbump.usecases.game.android.input.AbstractTouchService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

public class LeftRightTouchService extends AbstractTouchService {

	private static final MyLog LOGGER = Logger
			.getLogger(LeftRightTouchService.class);

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

	@Override
	public void destroy() {
	}

}
