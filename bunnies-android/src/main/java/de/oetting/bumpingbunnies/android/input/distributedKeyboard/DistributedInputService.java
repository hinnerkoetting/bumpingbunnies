package de.oetting.bumpingbunnies.android.input.distributedKeyboard;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.input.VibratorService;
import de.oetting.bumpingbunnies.android.input.gamepad.KeyboardInputService;
import de.oetting.bumpingbunnies.core.input.InputService;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class DistributedInputService implements InputService, KeyboardInputService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DistributedInputService.class);

	private Bunny playerMovement;
	private final VibratorService vibrator;

	private Context context;

	public DistributedInputService(Bunny playerMovement, VibratorService vibrator, Context context) {
		this.playerMovement = playerMovement;
		this.vibrator = vibrator;
		this.context = context;
	}

	@Override
	public boolean onButtonTouch(View v, MotionEvent event) {
		boolean isPressed = event.getAction() != MotionEvent.ACTION_UP;
		if (android.os.Build.VERSION.SDK_INT < 11) {
			workaroundMultitouchForOldDevices(v, event, isPressed);
			return true;
		}
		move(v, event, isPressed);
		return true;
	}

	private void move(View v, MotionEvent event, boolean isPressed) {
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
		}
	}

	private void workaroundMultitouchForOldDevices(View view, MotionEvent event, boolean pressed) {
		playerMovement.setNotMoving();
		playerMovement.setJumping(false);
		for (int pointer = 0; pointer < event.getPointerCount(); pointer++) {
			if (event.getX(pointer) + view.getLeft() < context.getResources().getDimension(R.dimen.button_width) )
				if (pressed)
					playerMovement.setMovingLeft();
				else
					playerMovement.setNotMoving();
			else if (event.getX(pointer) + view.getLeft() < context.getResources().getDimension(R.dimen.button_width ) * 2)
				if (pressed)
					playerMovement.setMovingRight();
				else
					playerMovement.setNotMoving();
			else
				playerMovement.setJumping(pressed);

		}
	}

	private void moveUp(boolean move) {
		if (move) {
			this.playerMovement.setJumping(true);
			this.vibrator.vibrate(R.id.button_up);
		} else {
			this.playerMovement.setJumping(false);
			this.vibrator.releaseVibrate(R.id.button_up);
		}
	}

	private void handleLeftRightMovement(View groupView, MotionEvent event, boolean isPressed) {
		if (isOnrightHalf(groupView, event)) {
			if (isPressed) {
				this.playerMovement.setMovingRight();
				this.vibrator.vibrate(R.id.button_right);
			} else {
				this.playerMovement.setNotMoving();
				this.vibrator.releaseVibrate(R.id.button_right);
			}
		} else {
			if (isPressed) {
				this.playerMovement.setMovingLeft();
				this.vibrator.vibrate(R.id.button_left);
			} else {
				this.playerMovement.setNotMoving();
				this.vibrator.releaseVibrate(R.id.button_left);
			}
		}
	}

	private boolean isOnrightHalf(View v, MotionEvent event) {
		LOGGER.debug("Event X %f - View left %d, View width %d", event.getX(), v.getLeft(), v.getWidth());
		return event.getRawX() > v.getLeft() + v.getWidth() / 2;
	}

}
