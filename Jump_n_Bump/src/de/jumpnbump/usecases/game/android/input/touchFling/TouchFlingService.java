package de.jumpnbump.usecases.game.android.input.touchFling;

import android.view.MotionEvent;
import de.jumpnbump.usecases.game.android.input.AbstractTouchService;
import de.jumpnbump.usecases.game.android.input.touch.LeftRightTouchService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;

public class TouchFlingService extends LeftRightTouchService implements
		AbstractTouchService {

	private float lastTouchedHeight;
	private float lastTouchedWidht;

	public TouchFlingService(PlayerMovementController playerMovement) {
		super(playerMovement);
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
		double diffY = this.lastTouchedHeight - motionEvent.getY();
		double diffX = Math.abs(motionEvent.getX() - this.lastTouchedWidht);
		return diffY > 0 && diffY * 2 > diffX;
	}

	private void rememberLastTouch(MotionEvent motionEvent) {
		if (motionEvent.getAction() != MotionEvent.ACTION_UP) {
			this.lastTouchedHeight = motionEvent.getY();
			this.lastTouchedWidht = motionEvent.getX();
		}
	}

	@Override
	public void setNewSize(int width, int height) {
		super.setNewSize(width, height);
		this.lastTouchedHeight = height;
	}

	@Override
	public void destroy() {
	}

}
