package de.oetting.bumpingbunnies.android.input.hardwareKeyboard;

import android.view.KeyEvent;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.core.input.InputService;

public class HardwareKeyboardInputService implements InputService {

	private final PlayerMovement movementController;
	private final boolean leftHanded;

	public HardwareKeyboardInputService(PlayerMovement movementController, boolean leftHanded) {
		this.movementController = movementController;
		this.leftHanded = leftHanded;
	}

	public boolean onKeyUp(int keyCode) {
		if (keyCode == getKeyLeft()) {
			this.movementController.removeLeftMovement();
			return true;
		}
		if (keyCode == getKeyRight()) {
			this.movementController.removeRightMovement();
			return true;
		}
		if (keyCode == getKeyUp()) {
			this.movementController.tryMoveDown();
			return true;
		}
		return false;
	}

	public boolean onKeyDown(int keyCode) {
		if (keyCode == getKeyLeft()) {
			this.movementController.tryMoveLeft();
			return true;
		}
		if (keyCode == getKeyRight()) {
			this.movementController.tryMoveRight();
			return true;
		}
		if (keyCode == getKeyUp()) {
			this.movementController.tryMoveUp();
			return true;
		}
		return false;
	}


	private int getKeyLeft() {
		if (leftHanded)
			return KeyEvent.KEYCODE_K;
		return KeyEvent.KEYCODE_S;
	}
	private int getKeyRight() {
		if (leftHanded)
			return KeyEvent.KEYCODE_L;
		return KeyEvent.KEYCODE_D;
	}

	private int getKeyUp() {
		if (leftHanded)
			return KeyEvent.KEYCODE_S;
		return KeyEvent.KEYCODE_K;
	}

}
