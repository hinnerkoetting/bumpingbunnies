package de.jumpnbump.usecases.game.android.input;

import android.view.MotionEvent;
import de.jumpnbump.usecases.game.businesslogic.GamePlayerController;
import de.jumpnbump.usecases.game.communication.StateSender;

public class TouchWithJumpService extends LeftRightTouchService {

	private boolean upIsPressed;

	public TouchWithJumpService(GamePlayerController playerMovement,
			StateSender sender) {
		super(playerMovement, sender);
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
