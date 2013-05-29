package de.jumpnbump.usecases.game.android.input.multiTouch;

import android.view.MotionEvent;
import de.jumpnbump.usecases.game.android.input.touch.LeftRightTouchService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

public class MultiTouchInputService extends LeftRightTouchService {

	private boolean shouldBeJumping;

	public MultiTouchInputService(PlayerMovementController playerMovement) {
		super(playerMovement);
	}

	@Override
	protected void executePlayerMovement() {
		super.executePlayerMovement();
		if (this.shouldBeJumping) {
			getPlayerMovement().tryMoveUp();
		} else {
			getPlayerMovement().tryMoveDown();
		}
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
		this.shouldBeJumping = true;
	}

	public void onMultiTouchRemoved(MotionEvent motionEvent) {
		this.shouldBeJumping = false;
	}

}
