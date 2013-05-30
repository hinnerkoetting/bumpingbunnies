package de.jumpnbump.usecases.game.android.input.touch;

import android.view.MotionEvent;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

public class TouchWithJumpService extends LeftRightTouchService {

	public TouchWithJumpService(PlayerMovementController playerMovement) {
		super(playerMovement);
	}

	public void onButtonTouchUp(MotionEvent event) {
		if (event.getAction() != MotionEvent.ACTION_UP) {
			rememberMoveUp();
		} else {
			rememberMoveDown();
		}
	}

}
