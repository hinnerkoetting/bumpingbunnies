package de.oetting.bumpingbunnies.usecases.game.android.input.touch;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovement;

public class TouchWithJumpService extends LeftRightTouchService {

	public TouchWithJumpService(PlayerMovement playerMovement,
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
