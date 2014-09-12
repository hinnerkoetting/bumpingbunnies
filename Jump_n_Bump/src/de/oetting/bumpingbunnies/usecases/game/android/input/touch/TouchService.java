package de.oetting.bumpingbunnies.usecases.game.android.input.touch;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;

public class TouchService extends LeftRightTouchService implements InputService {

	public TouchService(PlayerMovement playerMovement,
			CoordinatesCalculation calculations) {
		super(playerMovement, calculations);
	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		super.onMotionEvent(motionEvent);
		if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
			if (clickOnUpperHalf(motionEvent)) {
				moveUp();
			} else {
				moveDown();
			}
		} else {
			moveDown();
		}

	}

}
