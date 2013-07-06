package de.oetting.bumpingbunnies.usecases.game.android.input.hardwareKeyboard;

import android.view.KeyEvent;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;

public class HardwareKeyboardInputService implements InputService {

	private boolean leftPressed;
	private boolean rightPressed;
	private boolean upPressed;
	private PlayerMovementController movementController;

	public HardwareKeyboardInputService(
			PlayerMovementController movementController) {
		this.movementController = movementController;
	}

	@Override
	public void executeUserInput() {
	}

	public void onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_S) {
			this.movementController.removeLeftMovement();
		}
		if (keyCode == KeyEvent.KEYCODE_D) {
			this.movementController.removeRightMovement();
		}
		if (keyCode == KeyEvent.KEYCODE_K) {
			this.movementController.tryMoveDown();
		}
	}

	public void onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_S) {
			this.movementController.tryMoveLeft();
		}
		if (keyCode == KeyEvent.KEYCODE_D) {
			this.movementController.tryMoveRight();
		}
		if (keyCode == KeyEvent.KEYCODE_K) {
			this.movementController.tryMoveUp();
		}
	}

}
