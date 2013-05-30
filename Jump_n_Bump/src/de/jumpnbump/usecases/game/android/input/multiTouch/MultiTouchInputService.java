package de.jumpnbump.usecases.game.android.input.multiTouch;

import android.view.MotionEvent;
import de.jumpnbump.usecases.game.android.input.touch.LeftRightTouchService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

public class MultiTouchInputService extends LeftRightTouchService {

	public MultiTouchInputService(PlayerMovementController playerMovement) {
		super(playerMovement);
	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		super.onMotionEvent(motionEvent);
		if (motionEvent.getPointerCount() > 1) {
			onMultiTouch(motionEvent);
		} else {
			onMultiTouchRemoved(motionEvent);
		}
	}

	public void onMultiTouch(MotionEvent motionEvent) {
		rememberMoveUp();
	}

	public void onMultiTouchRemoved(MotionEvent motionEvent) {
		rememberMoveDown();
	}

}
