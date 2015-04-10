package de.oetting.bumpingbunnies.android.input.gamepad;

import android.view.MotionEvent;
import android.view.View;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class GamepadInputService implements KeyboardInputService {

	private Bunny playerMovement;

	public GamepadInputService(Bunny playerMovement) {
		this.playerMovement = playerMovement;
	}

	@Override
	public boolean onButtonTouch(View v, MotionEvent event) {
		boolean isPressed = event.getAction() != MotionEvent.ACTION_UP;
		if (!isPressed) {
			this.playerMovement.setNotMoving();
			return true;
		}
		switch (v.getId()) {
		case R.id.button_down:
			this.playerMovement.setJumping(false);
			break;
		case R.id.button_up:
			this.playerMovement.setJumping(true);
			break;
		case R.id.button_left:
			this.playerMovement.setMovingLeft();
			break;
		case R.id.button_right:
			this.playerMovement.setMovingRight();
			break;
		default:
			return false;
		}
		return true;
	}

}
