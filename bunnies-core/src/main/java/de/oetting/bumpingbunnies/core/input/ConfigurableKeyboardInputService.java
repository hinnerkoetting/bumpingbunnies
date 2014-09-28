package de.oetting.bumpingbunnies.core.input;

import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;

public class ConfigurableKeyboardInputService implements InputService {

	private final String leftKey;
	private final String rightKey;
	private final String upKey;
	private PlayerMovement movementController;

	public ConfigurableKeyboardInputService(String leftKey, String rightKey, String upKey, PlayerMovement movementController) {
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.upKey = upKey;
		this.movementController = movementController;
	}

	public boolean onKeyUp(String keyName) {
		if (keyName.equals(leftKey)) {
			this.movementController.removeLeftMovement();
			return true;
		}
		if (keyName.equals(rightKey)) {
			this.movementController.removeRightMovement();
			return true;
		}
		if (keyName.equals(upKey)) {
			this.movementController.tryMoveDown();
			return true;
		}
		return false;
	}

	public boolean onKeyDown(String keyName) {
		if (keyName.equals(leftKey)) {
			this.movementController.tryMoveLeft();
			return true;
		}
		if (keyName.equals(rightKey)) {
			this.movementController.tryMoveRight();
			return true;
		}
		if (keyName.equals(upKey)) {
			this.movementController.tryMoveUp();
			return true;
		}
		return false;
	}
}
