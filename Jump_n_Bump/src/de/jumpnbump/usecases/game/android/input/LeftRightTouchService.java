package de.jumpnbump.usecases.game.android.input;

import android.view.MotionEvent;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.businesslogic.GameScreenSizeChangeListener;
import de.jumpnbump.usecases.game.businesslogic.PlayerMovementController;
import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.model.Player;

public class LeftRightTouchService implements GameScreenSizeChangeListener,
		InputService {

	private static final MyLog LOGGER = Logger
			.getLogger(LeftRightTouchService.class);
	private MotionEvent lastEvent;
	private int windowWidth;
	private int windowHeight;
	private PlayerMovementController playerMovement;
	private StateSender sender;

	public LeftRightTouchService(PlayerMovementController playerMovement,
			StateSender sender) {
		this.playerMovement = playerMovement;
		this.sender = sender;
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
		this.sender.sendPlayerCoordinates(this.playerMovement.getPlayer());
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

	private void removePlayerMovement() {
		this.playerMovement.removeMovement();

	}

	/**
	 * Is overriden
	 */
	protected void executePlayerMovement() {
		int movement = 10;
		moveLeftOrRight(movement);
	}

	protected void moveLeftOrRight(int movement) {
		Player player = this.playerMovement.getPlayer();
		if (isClickLeftToPlayer(player)) {
			this.playerMovement.tryMoveLeft();
		} else if (isClickRightToPlayer(player)) {
			this.playerMovement.tryMoveRight();
		} else {
			this.playerMovement.removeMovement();
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
		this.sender.cancel();
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

	public PlayerMovementController getPlayerMovement() {
		return this.playerMovement;
	}

}
