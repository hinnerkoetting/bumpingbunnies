package de.jumpnbump.usecases.game.android.input.touchPress;

import android.view.MotionEvent;
import de.jumpnbump.usecases.game.android.input.AbstractTouchService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

public class TouchPressInputService extends AbstractTouchService {

	private boolean moveRight;
	private boolean moveLeft;
	private boolean moveUp;
	private long timeOfActionDown;

	public TouchPressInputService(PlayerMovementController playerMovement) {
		super(playerMovement);
	}

	@Override
	public void executeUserInput() {
		if (this.moveRight) {
			moveRight();
		}
		if (this.moveLeft) {
			moveLeft();
		}
		if (this.moveUp) {
			moveUp();
		}
	}

	@Override
	public void destroy() {

	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			reset();
		} else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			handleMotionDown(motionEvent);
		} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
			handleMotionPress();
		}
	}

	private void handleMotionPress() {
		if (System.currentTimeMillis() - this.timeOfActionDown > 100) {
			this.moveUp = true;

		}
	}

	private void handleMotionDown(MotionEvent motionEvent) {
		this.moveUp = false;
		this.timeOfActionDown = System.currentTimeMillis();
		if (isTouchRightToPlayer(motionEvent)) {
			this.moveRight = true;
		}
		if (isTouchLeftToPlayer(motionEvent)) {
			this.moveLeft = true;
		}
	}

	@Override
	public void reset() {
		super.reset();
		this.moveRight = false;
		this.moveLeft = false;
		this.moveUp = false;
		this.timeOfActionDown = System.currentTimeMillis();
	}

}
