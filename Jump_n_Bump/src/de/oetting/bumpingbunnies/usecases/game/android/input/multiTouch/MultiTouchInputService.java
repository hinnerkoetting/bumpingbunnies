package de.oetting.bumpingbunnies.usecases.game.android.input.multiTouch;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.LeftRightTouchService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;

public class MultiTouchInputService extends LeftRightTouchService {

	public MultiTouchInputService(PlayerMovementController playerMovement,
			CoordinatesCalculation coordinateCalculations) {
		super(playerMovement, coordinateCalculations);
	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		super.onMotionEvent(motionEvent);
		if (motionEvent.getPointerCount() > 1) {
			onMultiTouch();
		} else {
			onMultiTouchRemoved();
		}
	}

	public void onMultiTouch() {
		rememberMoveUp();
	}

	public void onMultiTouchRemoved() {
		rememberMoveDown();
	}

}
