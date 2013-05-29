package de.jumpnbump.usecases.game.android.input.pointer;

import android.view.MotionEvent;
import de.jumpnbump.usecases.game.android.input.PathFinder.PathFinder;
import de.jumpnbump.usecases.game.android.input.touch.LeftRightTouchService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.model.Player;

public class PointerInputService extends LeftRightTouchService {

	private static final double MAX_DISTANCE_TO_JUMP = 0.2;
	private final PathFinder pathFinder;
	private boolean moveUp;

	public PointerInputService(PlayerMovementController playerMovement,
			PathFinder pathFinder) {
		super(playerMovement);
		this.pathFinder = pathFinder;
	}

	@Override
	public void executeUserInput() {
		super.executeUserInput();
		if (this.moveUp) {
			getPlayerMovement().tryMoveUp();
		} else {
			getPlayerMovement().tryMoveDown();
		}
	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		super.onMotionEvent(motionEvent);
		if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
			this.moveUp = false;
		} else {
			this.moveUp = shouldBeReachedByJumping(motionEvent);
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
		Player player = getPlayerMovement().getPlayer();
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
