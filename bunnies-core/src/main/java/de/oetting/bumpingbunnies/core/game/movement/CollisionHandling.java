package de.oetting.bumpingbunnies.core.game.movement;

import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.game.objects.PlayerSimulation;
import de.oetting.bumpingbunnies.model.game.objects.Water;

public class CollisionHandling {

	private final MusicPlayer waterMusicPlayer;

	public CollisionHandling(MusicPlayer waterMusicPlayer) {
		this.waterMusicPlayer = waterMusicPlayer;
	}

	public void interactWithWater(PlayerSimulation nextStep, Player player, Water object, CollisionDetection collisionDetection) {
		player.setExactMovementY((int) Math.round(player.movementY() * 0.99));
		if (player.movementY() <= ModelConstants.PLAYER_SPEED_WATER) {
			player.setExactMovementY(ModelConstants.PLAYER_SPEED_WATER);
		}
		player.setAccelerationY(ModelConstants.PLAYER_GRAVITY_WATER);
		if (isFirstTimeThePlayerHitsTheWater(player, object, collisionDetection)) {
			object.playMusic();
		}
	}

	public void interactWith(Player player, GameObject fixedObject, CollisionDetection collisionDetection) {
		reducePlayerTooMaxSpeedToNotCollide(player, fixedObject, collisionDetection);

		GameObject updatedNextStep = player.simulateNextStep();
		if (collisionDetection.isExactlyUnderObject(updatedNextStep, fixedObject)) {
			fixedObject.interactWithPlayerOnTop(player);
		} else if (collisionDetection.isExactlyOverObject(updatedNextStep, fixedObject)) {
		}
	}

	/**
	 * If the simulated player is in the water and the player is not in the
	 * water this is the first time the player hits the water.
	 */
	private boolean isFirstTimeThePlayerHitsTheWater(Player player, Water water, CollisionDetection collisionDetection) {
		return !collisionDetection.collides(water, player);
	}

	private void reducePlayerTooMaxSpeedToNotCollide(Player player, GameObject object, CollisionDetection collisionDetection) {
		reduceXSpeed(player, object, collisionDetection);
		reduceYSpeed(player, object, collisionDetection);
	}

	private void reduceXSpeed(Player player, GameObject object, CollisionDetection collisionDetection) {
		GameObject nextStepX = player.simulateNextStepX();
		if (collisionDetection.collides(nextStepX, object)) {
			if (player.movementX() > 0) {
				int diffX = (int) (object.minX() - player.maxX());
				player.setExactMovementX(diffX);
				player.setAccelerationX(0);
			} else if (player.movementX() < 0) {
				int diffX = (int) (object.maxX() - player.minX());
				player.setExactMovementX(diffX);
				player.setAccelerationX(0);
			}
		}
	}

	private void reduceYSpeed(Player player, GameObject object, CollisionDetection collisionDetection) {
		GameObject nextStepY = player.simulateNextStepY();
		if (collisionDetection.collides(nextStepY, object)) {
			if (player.movementY() > 0) {
				int diffY = (int) (object.minY() - player.maxY());
				player.setExactMovementY(diffY);
				player.setAccelerationY(0);
			} else if (player.movementY() < 0) {
				int diffY = (int) (object.maxY() - player.minY());
				player.setExactMovementY(diffY);
				player.setAccelerationY(0);
			}
		}
	}

}
