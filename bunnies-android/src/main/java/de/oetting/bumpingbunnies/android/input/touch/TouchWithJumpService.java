package de.oetting.bumpingbunnies.android.input.touch;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class TouchWithJumpService extends LeftRightTouchService {

	public TouchWithJumpService(Bunny playerMovement,
			CoordinatesCalculation calculations) {
		super(playerMovement, calculations);
	}

	public void onButtonTouchUp(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_UP) {
			moveUp();
		} else {
			moveDown();
		}
	}

}
