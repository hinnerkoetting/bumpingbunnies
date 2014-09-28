package de.oetting.bumpingbunnies.core.input;

import java.util.HashMap;
import java.util.Map;

import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;

public class ConfigurableKeyboardInputService implements InputService {

	private final String leftKey;
	private final String rightKey;
	private final String upKey;
	private PlayerMovement movementController;
	private Map<String, Boolean> keyState = new HashMap<String, Boolean>();

	public ConfigurableKeyboardInputService(String leftKey, String rightKey, String upKey, PlayerMovement movementController) {
		this.leftKey = leftKey;
		this.rightKey = rightKey;
		this.upKey = upKey;
		this.movementController = movementController;
		keyState.put(leftKey, false);
		keyState.put(rightKey, false);
		keyState.put(upKey, false);
	}

	public boolean onKeyUp(String keyName) {
		boolean relevant = isRelevant(keyName);
		if (relevant) {
			keyState.put(keyName, true);
			evaluateKeys();
			return true;
		}
		return false;
	}

	public boolean onKeyDown(String keyName) {
		boolean relevant = isRelevant(keyName);
		if (relevant) {
			keyState.put(keyName, false);
			evaluateKeys();
			return true;
		}
		return false;
	}

	private boolean isRelevant(String keyName) {
		return keyName.equals(leftKey) || keyName.equals(rightKey) || keyName.equals(upKey);
	}

	private void evaluateKeys() {
		if (keyState.get(upKey).booleanValue())
			movementController.tryMoveUp();
		else
			movementController.tryMoveDown();
		if (keyState.get(rightKey).booleanValue()) {
			movementController.removeLeftMovement();
			movementController.tryMoveRight();
		} else if (keyState.get(leftKey).booleanValue()) {
			movementController.removeRightMovement();
			movementController.tryMoveLeft();
		} else
			movementController.removeHorizontalMovement();
	}
}
