package de.oetting.bumpingbunnies.android.input.pointer;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.android.input.pathFinder.PathFinder;
import de.oetting.bumpingbunnies.android.input.touch.LeftRightTouchService;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class PointerInputService extends LeftRightTouchService {

	private final PathFinder pathFinder;

	public PointerInputService(Bunny playerMovement, PathFinder pathFinder, CoordinatesCalculation calculations) {
		super(playerMovement, calculations);
		this.pathFinder = pathFinder;
	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		super.onMotionEvent(motionEvent);
		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			moveDown();
		} else {
			if (shouldBeReachedByJumping(motionEvent)) {
				moveUp();
			} else {
				moveDown();
			}
		}
	}

	private boolean shouldBeReachedByJumping(MotionEvent motionEvent) {
		if (isTooCloseToJump(motionEvent)) {
			return false;
		} else {
			return canBeReachedByJumping(motionEvent);
		}
	}

	private boolean isTooCloseToJump(MotionEvent motionEvent) {
		Bunny player = getMovedPlayer();
		double diffY = calculateDiffY(player, motionEvent);
		return Math.abs(diffY) < 0.1;
	}

	private boolean canBeReachedByJumping(MotionEvent motionEvent) {
		return this.pathFinder.canBeReachedByJumping(relativePointerPositionX(motionEvent), relativePointerPositionY(motionEvent));
	}

	private double calculateDiffY(Bunny player, MotionEvent motionEvent) {
		double relativeClickPositionY = relativePointerPositionY(motionEvent);
		return player.getCenterY() - relativeClickPositionY;
	}
}
