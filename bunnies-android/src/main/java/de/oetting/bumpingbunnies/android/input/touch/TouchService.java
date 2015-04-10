package de.oetting.bumpingbunnies.android.input.touch;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.input.InputService;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class TouchService extends LeftRightTouchService implements InputService {

	public TouchService(Bunny playerMovement, CoordinatesCalculation calculations) {
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
