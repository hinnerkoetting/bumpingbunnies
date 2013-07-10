package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class CollisionHandling {

	public void interactWith(Player player, GameObject fixedObject,
			CollisionDetection collisionDetection) {
		reducePlayerTooMaxSpeedToNotCollide(player, fixedObject,
				collisionDetection);

		GameObject updatedNextStep = player.simulateNextStep();
		if (collisionDetection.isExactlyUnderObject(updatedNextStep,
				fixedObject)) {
			fixedObject.interactWithPlayerOnTop(player);
		} else if (collisionDetection.isExactlyOverObject(updatedNextStep,
				fixedObject)) {
		}
	}

	private void reducePlayerTooMaxSpeedToNotCollide(Player player,
			GameObject object, CollisionDetection collisionDetection) {
		reduceXSpeed(player, object, collisionDetection);
		reduceYSpeed(player, object, collisionDetection);
	}

	private void reduceXSpeed(Player player, GameObject object,
			CollisionDetection collisionDetection) {
		GameObject nextStepX = player.simulateNextStepX();
		if (collisionDetection.collides(nextStepX, object)) {
			if (player.movementX() > 0) {
				int diffX = (int) (object.minX() - player.maxX());
				player.setMovementX(diffX);
				player.setAccelerationX(0);
			} else if (player.movementX() < 0) {
				int diffX = (int) (object.maxX() - player.minX());
				player.setMovementX(diffX);
				player.setAccelerationX(0);
			}
		}
	}

	private void reduceYSpeed(Player player, GameObject object,
			CollisionDetection collisionDetection) {
		GameObject nextStepY = player.simulateNextStepY();
		if (collisionDetection.collides(nextStepY, object)) {
			if (player.movementY() > 0) {
				int diffY = (int) (object.minY() - player.maxY());
				player.setMovementY(diffY);
				player.setAccelerationY(0);
			} else if (player.movementY() < 0) {
				int diffY = (int) (object.maxY() - player.minY());
				player.setMovementY(diffY);
				player.setAccelerationY(0);
			}
		}
	}
}
