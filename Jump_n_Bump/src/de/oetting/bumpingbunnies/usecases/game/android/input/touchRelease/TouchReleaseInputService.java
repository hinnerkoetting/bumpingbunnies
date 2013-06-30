package de.oetting.bumpingbunnies.usecases.game.android.input.touchRelease;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.AbstractTouchService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;

public class TouchReleaseInputService extends AbstractTouchService {

	public TouchReleaseInputService(PlayerMovementController playerMovement,
			CoordinatesCalculation coordinateCalculations) {
		super(playerMovement, coordinateCalculations);
	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		if (isTouchLeftToPlayer(motionEvent)) {
			rememberMoveLeft();
		}
		if (isTouchRightToPlayer(motionEvent)) {
			rememberMoveRight();
		}

		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			rememberMoveUp();
		} else {
			rememberMoveDown();
		}
	}

	@Override
	public void executeUserInput() {
		executeRememberedMovement();
	}

	@Override
	public void destroy() {
	}

}
