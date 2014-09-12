package de.oetting.bumpingbunnies.usecases.game.android.input.pointer;

import android.view.MotionEvent;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.PathFinder.PathFinder;
import de.oetting.bumpingbunnies.usecases.game.android.input.touch.LeftRightTouchService;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PointerInputService extends LeftRightTouchService {

	private final PathFinder pathFinder;

	public PointerInputService(PlayerMovement playerMovement,
			PathFinder pathFinder, CoordinatesCalculation calculations) {
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
		Player player = getMovedPlayer();
		double diffY = calculateDiffY(player, motionEvent);
		return Math.abs(diffY) < 0.1;
	}

	private boolean canBeReachedByJumping(MotionEvent motionEvent) {
		return this.pathFinder.canBeReachedByJumping(
				relativePointerPositionX(motionEvent),
				relativePointerPositionY(motionEvent));
	}

	private double calculateDiffY(Player player, MotionEvent motionEvent) {
		double relativeClickPositionY = relativePointerPositionY(motionEvent);
		return player.getCenterY() - relativeClickPositionY;
	}
}
