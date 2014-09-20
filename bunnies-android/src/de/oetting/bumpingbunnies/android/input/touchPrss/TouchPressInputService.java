package de.oetting.bumpingbunnies.android.input.touchPrss;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.android.input.AbstractTouchService;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class TouchPressInputService extends AbstractTouchService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(TouchPressInputService.class);

	private long timeOfActionDown;
	private double targetX;

	public TouchPressInputService(PlayerMovement playerMovement,
			CoordinatesCalculation coordinateCalculations) {
		super(playerMovement, coordinateCalculations);
	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			moveDown();
		} else if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			handleMotionDown(motionEvent);
		} else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
			handleMotionPress(motionEvent);
		}
		decideMoveLeftRight();
	}

	private void handleMotionPress(MotionEvent motionEvent) {
		if (System.currentTimeMillis() - this.timeOfActionDown > 100) {
			moveUp();
		}
		this.targetX = translateToGameXCoordinate(motionEvent);
	}

	private void handleMotionDown(MotionEvent motionEvent) {
		moveDown();
		this.timeOfActionDown = System.currentTimeMillis();
		this.targetX = translateToGameXCoordinate(motionEvent);
	}

	private void decideMoveLeftRight() {
		LOGGER.verbose("target x: %f - player x %f", this.targetX,
				getMovedPlayer().centerX());
		if (this.targetX > getMovedPlayer().maxX()) {
			moveRight();
		} else if (this.targetX < getMovedPlayer().minX()) {
			moveLeft();
		} else {
			removeHorizontalMovement();
		}
	}

}
