package de.jumpnbump.usecases.game.android.input;

import android.view.MotionEvent;
import android.view.View;
import de.jumpnbump.R;
import de.jumpnbump.usecases.game.businesslogic.GamePlayerController;
import de.jumpnbump.usecases.game.network.StateSender;

public class GamepadInputService implements InputService {

	private boolean leftIsPressed;
	private boolean rightIsPressed;
	private boolean upIsPressed;
	private boolean downIsPressed;

	private GamePlayerController playerMovement;
	private StateSender sender;

	public GamepadInputService(GamePlayerController playerMovement,
			StateSender stateSender) {
		this.playerMovement = playerMovement;
		this.sender = stateSender;
	}

	@Override
	public void executeUserInput() {
		handleHorizontalMovement();
		handleVerticalMovement();
		this.sender.sendPlayerCoordinates(this.playerMovement.getPlayer());
	}

	private void handleVerticalMovement() {
		if (this.downIsPressed == this.upIsPressed) {
			this.playerMovement.removeVerticalMovement();
		}
		if (this.downIsPressed) {
			this.playerMovement.tryMoveDown();
		}
		if (this.upIsPressed) {
			this.playerMovement.tryMoveUp();
		}
	}

	private void handleHorizontalMovement() {
		if (this.leftIsPressed == this.rightIsPressed) {
			this.playerMovement.removeHorizontalMovement();
		} else {
			if (this.leftIsPressed) {
				this.playerMovement.tryMoveLeft();
			}
			if (this.rightIsPressed) {
				this.playerMovement.tryMoveRight();
			}
		}
	}

	@Override
	public void destroy() {
	}

	public void onButtonTouch(View v, MotionEvent event) {
		boolean isPressed = event.getAction() != MotionEvent.ACTION_UP;
		switch (v.getId()) {
		case R.id.button_down:
			this.downIsPressed = isPressed;
			break;
		case R.id.button_up:
			this.upIsPressed = isPressed;
			break;
		case R.id.button_left:
			this.leftIsPressed = isPressed;
			break;
		case R.id.button_right:
			this.rightIsPressed = isPressed;
			break;

		}
	}
}
