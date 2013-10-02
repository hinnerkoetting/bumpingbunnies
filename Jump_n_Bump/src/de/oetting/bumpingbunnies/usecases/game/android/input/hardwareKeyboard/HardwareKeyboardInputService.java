package de.oetting.bumpingbunnies.usecases.game.android.input.hardwareKeyboard;

import android.view.KeyEvent;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;

public class HardwareKeyboardInputService implements InputService {

	private PlayerMovementController movementController;

	public HardwareKeyboardInputService(
			PlayerMovementController movementController) {
		this.movementController = movementController;
	}

	public boolean onKeyUp(int keyCode) {
		if (keyCode == KeyEvent.KEYCODE_S) {
			this.movementController.removeLeftMovement();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_D) {
			this.movementController.removeRightMovement();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_K) {
			this.movementController.tryMoveDown();
			return true;
		}
		return false;
	}

	public boolean onKeyDown(int keyCode) {
		if (keyCode == KeyEvent.KEYCODE_S) {
			this.movementController.tryMoveLeft();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_D) {
			this.movementController.tryMoveRight();
			return true;
		}
		if (keyCode == KeyEvent.KEYCODE_K) {
			this.movementController.tryMoveUp();
			return true;
		}
		return false;
	}

}
