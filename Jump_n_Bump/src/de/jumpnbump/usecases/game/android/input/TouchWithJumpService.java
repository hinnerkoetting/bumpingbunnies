package de.jumpnbump.usecases.game.android.input;

import android.view.MotionEvent;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

public class TouchWithJumpService extends LeftRightTouchService {

	private boolean upIsPressed;

	public TouchWithJumpService(PlayerMovementController playerMovement) {
		super(playerMovement);
	}

	@Override
	protected void executePlayerMovement() {
		super.executePlayerMovement();
		if (this.upIsPressed) {
			getPlayerMovement().tryMoveUp();
		} else {
			getPlayerMovement().tryMoveDown();
		}
	}

	public void onButtonTouchUp(MotionEvent event) {
		this.upIsPressed = event.getAction() != MotionEvent.ACTION_UP;
	}

}
