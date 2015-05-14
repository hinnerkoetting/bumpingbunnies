package de.oetting.bumpingbunnies.android.input.hardwareKeyboard;

import android.view.KeyEvent;
import de.oetting.bumpingbunnies.core.input.InputService;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class HardwareKeyboardInputService implements InputService {

	private final Bunny movedPlayer;
	private final boolean leftHanded;

	public HardwareKeyboardInputService(Bunny movedPl1ayer, boolean leftHanded) {
		movedPlayer = movedPl1ayer;
		this.leftHanded = leftHanded;
	}

	public boolean onKeyUp(int keyCode) {
		if (isKeyLeft(keyCode)) {
			if (this.movedPlayer.getAccelerationX() < 0)
				movedPlayer.setNotMoving();
			return true;
		}
		if (isKeyRight(keyCode)) {
			if (this.movedPlayer.getAccelerationX() > 0)
				movedPlayer.setNotMoving();
			return true;
		}
		if (isKeyUp(keyCode)) {
			this.movedPlayer.setJumping(false);
			return true;
		}
		return false;
	}

	public boolean onKeyDown(int keyCode) {
		if (isKeyLeft(keyCode)) {
			movedPlayer.setMovingLeft();
			return true;
		}
		if (isKeyRight(keyCode)) {
			movedPlayer.setMovingRight();
			return true;
		}
		if (isKeyUp(keyCode)) {
			movedPlayer.setJumping(true);
			return true;
		}
		return false;
	}

	private boolean isKeyUp(int keyCode) {
		return keyCode == getKeyUp() || keyCode == KeyEvent.KEYCODE_DPAD_UP;
	}

	private boolean isKeyRight(int keyCode) {
		return keyCode == getKeyRight() || keyCode == KeyEvent.KEYCODE_DPAD_RIGHT;
	}

	private boolean isKeyLeft(int keyCode) {
		return keyCode == getKeyLeft() || keyCode == KeyEvent.KEYCODE_DPAD_LEFT;
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
