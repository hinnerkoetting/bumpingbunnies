package de.jumpnbump.usecases.game.android.input.touch;

import android.view.MotionEvent;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

public class TouchWithJumpService extends LeftRightTouchService {

	private boolean upIsPressed;

	public TouchWithJumpService(PlayerMovementController playerMovement) {
		super(playerMovement);
	}

	@Override
	public void executeUserInput() {
		super.executeUserInput();
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
