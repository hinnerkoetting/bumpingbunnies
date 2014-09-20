package de.oetting.bumpingbunnies.android.input.touchRelease;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.android.input.AbstractTouchService;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;

public class TouchReleaseInputService extends AbstractTouchService {

	public TouchReleaseInputService(PlayerMovement playerMovement,
			CoordinatesCalculation coordinateCalculations) {
		super(playerMovement, coordinateCalculations);
	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		if (isTouchLeftToPlayer(motionEvent)) {
			moveLeft();
		}
		if (isTouchRightToPlayer(motionEvent)) {
			moveRight();
		}

		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			moveUp();
		} else {
			moveDown();
		}
	}

}
