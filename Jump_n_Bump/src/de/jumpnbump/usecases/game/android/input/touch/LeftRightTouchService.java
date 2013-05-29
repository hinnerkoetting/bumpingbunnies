package de.jumpnbump.usecases.game.android.input.touch;

import android.view.MotionEvent;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.android.input.AbstractTouchService;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.model.Player;

public class LeftRightTouchService extends AbstractTouchService {

	private static final MyLog LOGGER = Logger
			.getLogger(LeftRightTouchService.class);
	private MotionEvent lastEvent;

	public LeftRightTouchService(PlayerMovementController playerMovement) {
		super(playerMovement);
	}

	@Override
	public void onMotionEvent(MotionEvent motionEvent) {
		this.lastEvent = motionEvent;
	}

	@Override
	public void executeUserInput() {
		if (this.lastEvent != null) {
			executeLastExistingEvent();
			removeLastEvent();
		}
	}

	private void removeLastEvent() {
		this.lastEvent = null;
	}

	private void executeLastExistingEvent() {
		if (this.lastEvent.getAction() != MotionEvent.ACTION_UP) {
			executePlayerMovement();
		} else {
			removePlayerMovement();
		}
	}

	/**
	 * Is overriden
	 */
	protected void executePlayerMovement() {
		int movement = 10;
		moveLeftOrRight(movement);
	}

	protected double relativePointerPositionX(MotionEvent motionEvent) {
		return translateToGameXCoordinate(motionEvent);
	}

	protected double relativePointerPositionY(MotionEvent motionEvent) {
		return translateToGameYCoordinate(motionEvent);
	}

	protected void moveLeftOrRight(int movement) {
		Player player = getMovedPlayer();
		PlayerMovementController playerMovement = getPlayerMovement();
		if (isClickLeftToPlayer(player)) {
			playerMovement.tryMoveLeft();
		} else if (isClickRightToPlayer(player)) {
			playerMovement.tryMoveRight();
		} else {
			playerMovement.removeMovement();
		}
	}

	private boolean isClickRightToPlayer(Player player) {
		return translateToGameXCoordinate(this.lastEvent) > 0.5;
	}

	private boolean isClickLeftToPlayer(Player player) {
		try {
			return translateToGameXCoordinate(this.lastEvent) < 0.5;
		} catch (IllegalArgumentException e) {
			LOGGER.warn("Exception during calling getX " + e.getMessage());
			return false;
		}
	}

	@Override
	public void destroy() {
	}

	public MotionEvent getLastEvent() {
		return this.lastEvent;
	}

}
