package de.oetting.bumpingbunnies.usecases.game.android.input.touchFling;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.LeftRightTouchService;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementController;

public class TouchFlingService extends LeftRightTouchService {

	private double lastTouchedHeight;
	private double lastTouchedWidht;

	public TouchFlingService(PlayerMovementController playerMovement,
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

	@Override
	public void setNewSize(int width, int height) {
		super.setNewSize(width, height);
	}

}
