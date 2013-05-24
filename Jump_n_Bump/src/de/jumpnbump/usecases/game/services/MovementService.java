package de.jumpnbump.usecases.game.services;

import android.view.MotionEvent;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public class MovementService {

	private static final MyLog LOGGER = Logger.getLogger(MovementService.class);
	private World world;
	private MotionEvent lastEvent;
	private int windowWidth;
	private int windowHeight;

	public MovementService(World world, int windowWidth, int windowHeight) {
		this.world = world;
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
			player1.setCenterX(player1.getCenterX() - movement);
		} else {
			player1.setCenterX(player1.getCenterX() + movement);
		}
	}

	private void moveUpOrDown(int movement, Player player1) {
		if (clickOnUpperHalf()) {
			player1.setCenterY(player1.getCenterY() - movement);
		} else {
			player1.setCenterY(player1.getCenterY() + movement);
		}
	}

	private boolean clickOnUpperHalf() {
		return getRelativeY() < 0.5f;
	}

	private boolean isClickOnLeftHalf() {
		return getRelativeX() < 0.5f;
	}

	public void setWindowWidth(int windowWidth) {
		this.windowWidth = windowWidth;
	}

	public void setWindowHeight(int windowHeight) {
		this.windowHeight = windowHeight;
	}

}
