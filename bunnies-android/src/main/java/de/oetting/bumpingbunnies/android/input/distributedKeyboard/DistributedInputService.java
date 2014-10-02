package de.oetting.bumpingbunnies.android.input.distributedKeyboard;

import android.view.MotionEvent;
import android.view.View;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.input.VibratorService;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.core.input.InputService;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.android.input.gamepad.KeyboardInputService;

public class DistributedInputService implements InputService,
		KeyboardInputService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(DistributedInputService.class);

	private PlayerMovement playerMovement;
	private final VibratorService vibrator;

	public DistributedInputService(PlayerMovement playerMovement,
			VibratorService vibrator) {
		this.playerMovement = playerMovement;
		this.vibrator = vibrator;
	}

	@Override
	public boolean onButtonTouch(View v, MotionEvent event) {
		boolean isPressed = event.getAction() != MotionEvent.ACTION_UP;
		switch (v.getId()) {
		case R.id.input_distributed_left_right:
			handleLeftRightMovement(v, event, isPressed);
			break;
		case R.id.button_up:
			moveUp(isPressed);
			break;
		case R.id.button_left:
			handleLeftRightMovement((View) v.getParent(), event, isPressed);
			break;
		case R.id.button_right:
			handleLeftRightMovement((View) v.getParent(), event, isPressed);
			break;
		default:
			return false;
		}
		return true;
	}

	private void moveUp(boolean move) {
		if (move) {
			this.playerMovement.tryMoveUp();
			this.vibrator.vibrate(R.id.button_up);
		} else {
			this.playerMovement.tryMoveDown();
			this.vibrator.releaseVibrate(R.id.button_up);
		}
	}

	private void handleLeftRightMovement(View groupView, MotionEvent event,
			boolean isPressed) {
		if (isOnrightHalf(groupView, event)) {
			if (isPressed) {
				this.playerMovement.tryMoveRight();
				this.vibrator.vibrate(R.id.button_right);
			} else {
				this.playerMovement.removeHorizontalMovement();
				this.vibrator.releaseVibrate(R.id.button_right);
			}
		} else {
			if (isPressed) {
				this.playerMovement.tryMoveLeft();
				this.vibrator.vibrate(R.id.button_left);
			} else {
				this.playerMovement.removeHorizontalMovement();
				this.vibrator.releaseVibrate(R.id.button_left);
			}
		}
	}

	private boolean isOnrightHalf(View v, MotionEvent event) {
		LOGGER.debug("Event X %f - View left %d, View width %d", event.getX(),
				v.getLeft(), v.getWidth());
		return event.getRawX() > v.getLeft() + v.getWidth() / 2;
	}

}
