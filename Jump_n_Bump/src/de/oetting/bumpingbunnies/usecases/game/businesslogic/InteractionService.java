package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.usecases.game.ObjectProvider;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class InteractionService {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(InteractionService.class);
	private CollisionDetection collisionDetection;

	public InteractionService(CollisionDetection collisionDetection) {
		this.collisionDetection = collisionDetection;
	}

	public final void interactWith(Player player, ObjectProvider world) {
		GameObject nextStep = player.simulateNextStep();
		// next step is updated in interactWith if player collides with objects
		for (GameObject object : world.getAllObjects()) {

			interactWith(nextStep, player, object);
		}
		for (Player p : world.getAllPlayer()) {
			if (p.id() != player.id()) {
				interactWith(nextStep, player, p);
			}
		}
	}

	private final void interactWith(GameObject nextStep, Player player,
			GameObject object) {
		if (this.collisionDetection.collides(nextStep, object)) {
			reducePlayerTooMaxSpeedToNotCollide(player, object);

			GameObject updatedNextStep = player.simulateNextStep();
			if (this.collisionDetection.isExactlyUnderObject(updatedNextStep,
					object)) {
				object.interactWithPlayerOnTop(player);
			} else if (this.collisionDetection.isExactlyOverObject(
					updatedNextStep, object)) {
				if (object instanceof Player) {
					player.interactWithPlayerOnTop((Player) object);
				}
			}
		}
	}

	private void reducePlayerTooMaxSpeedToNotCollide(Player player,
			GameObject object) {
		reduceXSpeed(player, object);
		reduceYSpeed(player, object);
	}

	private void reduceXSpeed(Player player, GameObject object) {
		GameObject nextStepX = player.simulateNextStepX();
		if (this.collisionDetection.collides(nextStepX, object)) {
			if (player.movementX() > 0) {
				int diffX = object.minX() - player.maxX();
				player.setMovementX(diffX);
				player.setAccelerationX(0);
			} else if (player.movementX() < 0) {
				int diffX = object.maxX() - player.minX();
				player.setMovementX(diffX);
				player.setAccelerationX(0);
			}
		}
	}

	private void reduceYSpeed(Player player, GameObject object) {
		GameObject nextStepY = player.simulateNextStepY();
		if (this.collisionDetection.collides(nextStepY, object)) {
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
