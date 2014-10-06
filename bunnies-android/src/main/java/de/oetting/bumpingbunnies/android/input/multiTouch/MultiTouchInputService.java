package de.oetting.bumpingbunnies.android.input.multiTouch;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.android.input.touch.LeftRightTouchService;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;

public class MultiTouchInputService extends LeftRightTouchService {

	public MultiTouchInputService(PlayerMovement playerMovement, CoordinatesCalculation coordinateCalculations) {
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
		moveUp();
	}

	public void onMultiTouchRemoved() {
		moveDown();
	}

}
