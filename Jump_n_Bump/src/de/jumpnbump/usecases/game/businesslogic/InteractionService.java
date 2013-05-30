package de.jumpnbump.usecases.game.businesslogic;

import de.jumpnbump.usecases.game.ObjectProvider;
import de.jumpnbump.usecases.game.model.GameObject;
import de.jumpnbump.usecases.game.model.Player;

public class InteractionService {

	private CollisionDetection collisionDetection;

	public InteractionService(CollisionDetection collisionDetection) {
		this.collisionDetection = collisionDetection;
	}

	public void interactWith(Player player, ObjectProvider world) {
		GameObject nextStep = player.simulateNextStep();
		for (GameObject object : world.getAllObjects()) {
			if (object.id() != player.id()) {
				interactWith(player, nextStep, object);
			}
		}
	}

	private void interactWith(Player player, GameObject nextStep,
			GameObject object) {
		if (this.collisionDetection.collides(player, object)) {
			reducePlayerTooMaxSpeedToNotCollide(player, object);
		}
	}

	private void reducePlayerTooMaxSpeedToNotCollide(Player player,
			GameObject object) {
		reduceXSpeed(player, object);
		reduceYSpeed(player, object);
	}

	private void reduceYSpeed(Player player, GameObject object) {
		if (player.movementY() > 0) {
			double diffY = object.minY() - player.maxY();
			player.setMovementY(diffY);
		} else if (player.movementY() < 0) {
			double diffY = object.maxY() - player.minY();
			player.setMovementY(diffY);
		}
	}

	private void reduceXSpeed(Player player, GameObject object) {
		if (player.movementX() > 0) {
			double diffX = object.minX() - player.maxX();
			player.setMovementX(diffX);
		} else if (player.movementX() < 0) {
			double diffX = object.maxX() - player.minX();
			player.setMovementX(diffX);
		}
	}

}
