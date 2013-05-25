package de.jumpnbump.usecases.game.businesslogic;

import android.view.MotionEvent;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public class TouchService implements GameScreenSizeChangeListener {

	private static final MyLog LOGGER = Logger.getLogger(TouchService.class);
	private World world;
	private MotionEvent lastEvent;
	private int windowWidth;
	private int windowHeight;
	private PlayerMovement playerMovement;

	public TouchService(World world, PlayerMovement playerMovement) {
		this.world = world;
		this.playerMovement = playerMovement;
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

	public void executeUserInput() {
		if (this.lastEvent != null) {
			if (this.lastEvent.getAction() != MotionEvent.ACTION_UP) {
				executePlayerMovement();
			}
		}
	}

	private void executePlayerMovement() {
		int movement = 10;
		Player player1 = this.world.getPlayer1();
		moveLeftOrRight(movement, player1);
		moveUpOrDown(movement, player1);
	}

	private void moveLeftOrRight(int movement, Player player1) {
		if (isClickOnLeftHalf()) {
			this.playerMovement.tryMoveLeft();
		} else {
			this.playerMovement.tryMoveRight();
		}
	}

	private void moveUpOrDown(int movement, Player player1) {
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
