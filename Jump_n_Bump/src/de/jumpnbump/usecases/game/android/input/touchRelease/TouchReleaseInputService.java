package de.jumpnbump.usecases.game.android.input.touchRelease;

import android.view.MotionEvent;
import de.jumpnbump.usecases.game.android.input.touch.LeftRightTouchService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

public class TouchReleaseInputService extends LeftRightTouchService {

	public TouchReleaseInputService(PlayerMovementController playerMovement) {
		super(playerMovement);
	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		super.onMotionEvent(motionEvent);
		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			rememberMoveUp();
		} else {
			rememberMoveDown();
		}
	}

}
