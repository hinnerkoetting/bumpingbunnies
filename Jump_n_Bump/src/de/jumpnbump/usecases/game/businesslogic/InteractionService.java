package de.jumpnbump.usecases.game.businesslogic;

import android.graphics.Color;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.ObjectProvider;
import de.jumpnbump.usecases.game.model.GameObject;
import de.jumpnbump.usecases.game.model.Player;

public class InteractionService {

	private static final MyLog LOGGER = Logger
			.getLogger(InteractionService.class);
	private CollisionDetection collisionDetection;

	public InteractionService(CollisionDetection collisionDetection) {
		this.collisionDetection = collisionDetection;
	}

	public void interactWith(Player player, ObjectProvider world) {
		GameObject nextStep = player.simulateNextStep();
		for (GameObject object : world.getAllObjects()) {
			if (object.id() != player.id()) {
				interactWith(nextStep, player, object);
			}
		}
	}

	private void interactWith(GameObject nextStep, Player player,
			GameObject object) {
		if (this.collisionDetection.collides(nextStep, object)) {
			reducePlayerTooMaxSpeedToNotCollide(player, object);
		}
	}

	private void reducePlayerTooMaxSpeedToNotCollide(Player player,
			GameObject object) {
		reduceXSpeed(player, object);
		reduceYSpeed(player, object);
	}

	private boolean used = false;

	private void reduceXSpeed(Player player, GameObject object) {
		if (this.collisionDetection.isLeftOrRightToObject(player, object)) {
			this.used = true;
			LOGGER.info("PlayerState %s", player.getState());
			LOGGER.info("colliding with object %d %s", object.id(),
					object.toString());
			if (player.movementX() > 0) {

				int diffX = object.minX() - player.maxX();

				object.setColor(Color.RED);
				LOGGER.info(String.format(
						"Reduces X speed %d -- %d -- %d -- %d", object.minX(),
						player.maxX(), diffX, player.movementX()));
				player.setMovementX(diffX);
				player.setAccelerationX(0);
			} else if (player.movementX() < 0) {
				int diffX = object.maxX() - player.minX();
				LOGGER.info(String.format("Reduces X speed %d - %d", diffX,
						player.movementX()));
				object.setColor(Color.CYAN);
				player.setMovementX(diffX);
				player.setAccelerationX(0);
			}
		}
	}

	private void reduceYSpeed(Player player, GameObject object) {
		if (this.collisionDetection.isOverOrUnderObject(player, object)) {
			if (player.movementY() > 0) {
				int diffY = object.minY() - player.maxY();
				player.setMovementY(diffY);
				player.setAccelerationY(0);
			} else if (player.movementY() < 0) {
				int diffY = object.maxY() - player.minY();
				player.setMovementY(diffY);
				player.setAccelerationY(0);
			}
		}
	}

}
