package de.oetting.bumpingbunnies.android.input.hardwareKeyboard;

import android.view.KeyEvent;
import de.oetting.bumpingbunnies.core.input.InputService;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class HardwareKeyboardInputService implements InputService {

	private final Player movedPlayer;
	private final boolean leftHanded;

	public HardwareKeyboardInputService(Player movedPl1ayer, boolean leftHanded) {
		movedPlayer = movedPl1ayer;
		this.leftHanded = leftHanded;
	}

	public boolean onKeyUp(int keyCode) {
		if (keyCode == getKeyLeft()) {
			if (this.movedPlayer.getAccelerationX() < 0) 
				movedPlayer.setNotMoving();
			return true;
		}
		if (keyCode == getKeyRight()) {
			if (this.movedPlayer.getAccelerationX() > 0) 
				movedPlayer.setNotMoving();
			return true;
		}
		if (keyCode == getKeyUp()) {
			this.movedPlayer.setJumping(false);
			return true;
		}
		return false;
	}

	public boolean onKeyDown(int keyCode) {
		if (keyCode == getKeyLeft()) {
			movedPlayer.setMovingLeft();
			return true;
		}
		if (keyCode == getKeyRight()) {
			movedPlayer.setMovingRight();
			return true;
		}
		if (keyCode == getKeyUp()) {
			movedPlayer.setJumping(true);
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
