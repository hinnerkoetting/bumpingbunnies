package de.oetting.bumpingbunnies.usecases.game.android.input.gamepad;

import android.view.MotionEvent;
import android.view.View;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;

public class GamepadInputService implements KeyboardInputService {

	private PlayerMovementController playerMovement;

	public GamepadInputService(PlayerMovementController playerMovement) {
		this.playerMovement = playerMovement;
	}

	@Override
	public boolean onButtonTouch(View v, MotionEvent event) {
		boolean isPressed = event.getAction() != MotionEvent.ACTION_UP;
		if (!isPressed) {
			this.playerMovement.removeMovement();
			return true;
		}
		switch (v.getId()) {
		case R.id.button_down:
			this.playerMovement.tryMoveDown();
			break;
		case R.id.button_up:
			this.playerMovement.tryMoveUp();
			break;
		case R.id.button_left:
			this.playerMovement.tryMoveLeft();
			break;
		case R.id.button_right:
			this.playerMovement.tryMoveRight();
			break;
		default:
			return false;
		}
		return true;
	}

}
