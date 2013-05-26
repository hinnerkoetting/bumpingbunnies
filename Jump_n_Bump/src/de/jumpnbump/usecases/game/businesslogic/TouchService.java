package de.jumpnbump.usecases.game.businesslogic;

import android.view.MotionEvent;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;
import de.jumpnbump.usecases.game.network.GameNetworkSendThread;

public class TouchService implements GameScreenSizeChangeListener,
		MovementService {

	private static final MyLog LOGGER = Logger.getLogger(TouchService.class);
	private World world;
	private MotionEvent lastEvent;
	private int windowWidth;
	private int windowHeight;
	private PlayerMovement playerMovement;
	private GameNetworkSendThread networkThread;

	public TouchService(World world, PlayerMovement playerMovement,
			GameNetworkSendThread networkThread) {
		this.world = world;
		this.playerMovement = playerMovement;
		this.networkThread = networkThread;
	}

	public void onMotionEvent(MotionEvent motionEvent) {
		this.lastEvent = motionEvent;
	}

	private float getRelativeX() {
		return this.lastEvent.getX() / this.windowWidth;
	}

	private float getRelativeY() {
		return this.lastEvent.getY() / this.windowHeight;
	}

	@Override
	public void executeUserInput() {
		if (this.lastEvent != null) {
			executeLastExistingEvent();
		}
		this.networkThread.sendPlayerCoordinates(this.playerMovement
				.getPlayer());
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

	private void executePlayerMovement() {
		int movement = 10;
		moveLeftOrRight(movement);
		moveUpOrDown(movement);
	}

	private void moveLeftOrRight(int movement) {
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
		return this.lastEvent.getX() < player.minX() * this.windowWidth;
	}

	private void moveUpOrDown(int movement) {
		if (clickOnUpperHalf()) {
			this.playerMovement.tryMoveUp();
		} else {
			this.playerMovement.tryMoveDown();
		}
	}

	private boolean clickOnUpperHalf() {
		return getRelativeY() < 0.5f;
	}

	private boolean isClickOnLeftHalf() {
		return getRelativeX() < 0.5f;
	}

	@Override
	public void setNewSize(int width, int height) {
		this.windowHeight = height;
		this.windowWidth = width;
	}

}
