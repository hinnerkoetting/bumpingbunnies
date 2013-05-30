package de.jumpnbump.usecases.game.android.input.touchPress;

import android.view.MotionEvent;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.android.input.AbstractTouchService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

public class TouchPressInputService extends AbstractTouchService {

	private static final MyLog LOGGER = Logger
			.getLogger(TouchPressInputService.class);

	private long timeOfActionDown;
	private double targetX;

	public TouchPressInputService(PlayerMovementController playerMovement) {
		super(playerMovement);
	}

	@Override
	public void destroy() {
	}

	@Override
	public void executeUserInput() {
		decideMoveLeftRight();
		executeRememberedMovement();
	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			rememberMoveDown();
		} else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			handleMotionDown(motionEvent);
		} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
			handleMotionPress(motionEvent);
		}
	}

	private void handleMotionPress(MotionEvent motionEvent) {
		if (System.currentTimeMillis() - this.timeOfActionDown > 100) {
			rememberMoveUp();
		}
		this.targetX = translateToGameXCoordinate(motionEvent);
	}

	private void handleMotionDown(MotionEvent motionEvent) {
		rememberMoveDown();
		this.timeOfActionDown = System.currentTimeMillis();
		this.targetX = translateToGameXCoordinate(motionEvent);
	}

	private void decideMoveLeftRight() {
		LOGGER.verbose("target x: %f - player x %f", this.targetX,
				getMovedPlayer().centerX());
		if (this.targetX > getMovedPlayer().maxX()) {
			rememberMoveRight();
		} else if (this.targetX < getMovedPlayer().minX()) {
			rememberMoveLeft();
		} else {
			removeHorizontalMovement();
		}
	}

}
