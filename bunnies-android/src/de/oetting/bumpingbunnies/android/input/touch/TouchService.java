package de.oetting.bumpingbunnies.android.input.touch;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.core.input.InputService;

public class TouchService extends LeftRightTouchService implements InputService {

	public TouchService(PlayerMovement playerMovement, CoordinatesCalculation calculations) {
		super(playerMovement, calculations);
	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		super.onMotionEvent(motionEvent);
		if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
			if (clickOnUpperHalf((int) motionEvent.getY())) {
				moveUp();
			} else {
				moveDown();
			}
		} else {
			moveDown();
		}

	}

}
