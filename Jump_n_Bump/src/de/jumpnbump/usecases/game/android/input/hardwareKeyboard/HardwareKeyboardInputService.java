package de.jumpnbump.usecases.game.android.input.hardwareKeyboard;

import android.view.KeyEvent;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

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
		if (this.leftPressed) {
			this.movementController.tryMoveLeft();
		}
		if (this.rightPressed) {
			this.movementController.tryMoveRight();
		}
		if (this.upPressed) {
			this.movementController.tryMoveUp();
		} else {
			this.movementController.tryMoveDown();
		}
	}

	public void onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_S) {
			this.leftPressed = false;
		}
		if (keyCode == KeyEvent.KEYCODE_D) {
			this.rightPressed = false;
		}
		if (keyCode == KeyEvent.KEYCODE_K) {
			this.upPressed = false;
		}
	}

	public void onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_S) {
			this.leftPressed = true;
		}
		if (keyCode == KeyEvent.KEYCODE_D) {
			this.rightPressed = true;
		}
		if (keyCode == KeyEvent.KEYCODE_K) {
			this.upPressed = true;
		}
	}

	@Override
	public void destroy() {
	}

}
