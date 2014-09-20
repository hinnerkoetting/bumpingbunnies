package de.oetting.bumpingbunnies.android.input.touchFling;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.LeftRightTouchService;

public class TouchFlingService extends LeftRightTouchService {

	private double lastTouchedHeight;
	private double lastTouchedWidht;

	public TouchFlingService(PlayerMovement playerMovement,
			CoordinatesCalculation coordinateCalculations) {
		super(playerMovement, coordinateCalculations);
	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		super.onMotionEvent(motionEvent);
		if (isFlingUp(motionEvent)) {
			moveUp();
		} else {
			moveDown();
		}
		rememberLastTouch(motionEvent);
	}

	private boolean isFlingUp(MotionEvent motionEvent) {
		if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
			return false;
		}
		double diffY = this.lastTouchedHeight
				- translateToGameYCoordinate(motionEvent);
		double diffX = Math.abs(translateToGameXCoordinate(motionEvent)
				- this.lastTouchedWidht);
		return diffY > 0 && diffY * 2 > diffX;
	}

	private void rememberLastTouch(MotionEvent motionEvent) {
		if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
			this.lastTouchedHeight = translateToGameYCoordinate(motionEvent);
			this.lastTouchedWidht = translateToGameXCoordinate(motionEvent);
		}
	}

}
