package de.jumpnbump.usecases.game.android.input.touch;

import android.view.MotionEvent;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.android.input.AbstractControlledMovement;
import de.jumpnbump.usecases.game.businesslogic.GameScreenSizeChangeListener;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.model.Player;

public class LeftRightTouchService extends AbstractControlledMovement implements
		GameScreenSizeChangeListener {

	private static final MyLog LOGGER = Logger
			.getLogger(LeftRightTouchService.class);
	private MotionEvent lastEvent;
	private int windowWidth;
	private int windowHeight;

	public LeftRightTouchService(PlayerMovementController playerMovement) {
		super(playerMovement);
	}

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
		return motionEvent.getX() / this.windowWidth;
	}

	protected double relativePointerPositionY(MotionEvent motionEvent) {
		return motionEvent.getY() / this.windowHeight;
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
		return this.lastEvent.getX() > player.maxX() * this.windowWidth;
	}

	private boolean isClickLeftToPlayer(Player player) {
		try {
			return this.lastEvent.getX() < player.minX() * this.windowWidth;
		} catch (IllegalArgumentException e) {
			LOGGER.warn("Exception during calling getX " + e.getMessage());
			return false;
		}
	}

	@Override
	public void setNewSize(int width, int height) {
		this.windowHeight = height;
		this.windowWidth = width;
	}

	@Override
	public void destroy() {
	}

	public MotionEvent getLastEvent() {
		return this.lastEvent;
	}

	public int getWindowWidth() {
		return this.windowWidth;
	}

	public int getWindowHeight() {
		return this.windowHeight;
	}

}
