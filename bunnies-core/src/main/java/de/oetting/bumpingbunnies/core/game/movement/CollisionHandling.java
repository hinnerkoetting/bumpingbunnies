package de.oetting.bumpingbunnies.core.game.movement;

import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.game.objects.PlayerSimulation;
import de.oetting.bumpingbunnies.model.game.objects.Water;

public class CollisionHandling {

	private final MusicPlayer waterMusicPlayer;
	private final MusicPlayer jumperMusicPlayer;

	public CollisionHandling(MusicPlayer waterMusicPlayer, MusicPlayer jumperMusicPlayer) {
		this.waterMusicPlayer = waterMusicPlayer;
		this.jumperMusicPlayer = jumperMusicPlayer;
	}

	public void interactWithWater(PlayerSimulation nextStep, Player player, Water object,
			CollisionDetection collisionDetection) {
		player.setExactMovementY((int) Math.round(player.movementY() * 0.99));
		if (player.movementY() <= ModelConstants.BUNNY_SPEED_WATER) {
			player.setExactMovementY(ModelConstants.BUNNY_SPEED_WATER);
		}
		player.setAccelerationY(ModelConstants.BUNNY_GRAVITY_WATER);
		if (isFirstTimeThePlayerHitsTheWater(player, object, collisionDetection)) {
			waterMusicPlayer.start();
		}
	}

	public void interactWithJumper(Player player, GameObject fixedObject, CollisionDetection collisionDetection) {
		interactWith(player, fixedObject, collisionDetection);
		GameObject updatedNextStep = player.simulateNextStep();
		if (collisionDetection.isExactlyUnderObject(updatedNextStep, fixedObject)) {
			handlePlayerStandingOnJumper(player);
		}
	}

	private void handlePlayerStandingOnJumper(Player player) {
		jumperMusicPlayer.start();
		player.setMovementY(ModelConstants.BUNNY_JUMP_SPEED_JUMPER);
		player.setAccelerationY(0);
		player.simulateNextStep();
	}

	public void interactWith(Player player, GameObject fixedObject, CollisionDetection collisionDetection) {
		reducePlayerTooMaxSpeedToNotCollide(player, fixedObject, collisionDetection);
	}

	/**
	 * If the simulated player is in the water and the player is not in the
	 * water this is the first time the player hits the water.
	 */
	private boolean isFirstTimeThePlayerHitsTheWater(Player player, Water water, CollisionDetection collisionDetection) {
		return !collisionDetection.collides(water, player);
	}

	private void reducePlayerTooMaxSpeedToNotCollide(Player player, GameObject object,
			CollisionDetection collisionDetection) {
		//Goal: We do not want the player to move into a wall.
		//Solution : We simulate the next step of the player.
		//if this step collides with an object (it overlaps with this object) we have to find out if the player has to reduce his x oder y speed.
		//We assume the player needs to reduce its X speed if was currently (as opposed to the next step) not in the same X position as the object (e.g. he is moving from the left into a wall).
		//The same is done for the Y position (e.g. the player is falling down into a wall).
		//Special case: If the player is currently not at the same x AND y position we only reduce the x velocity. Otherwise the player could get stuck if he was exactly at a diagonal position to a wall.
		//I am not sure if there is better solution to this problem. It is the result of many failing attempts.
		GameObject nextStep = player.simulateNextStep();
		if (collisionDetection.collides(nextStep, object)) {
			if (!collisionDetection.sharesHorizontalPosition(player, object))
				reduceXSpeed(player, object);
			if (!collisionDetection.sharesVerticalPosition(player, object) && collisionDetection.sharesHorizontalPosition(player, object))
				reduceYSpeed(player, object);
		}
	}

	private void reduceXSpeed(Player player, GameObject object) {
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

	private void reduceYSpeed(Player player, GameObject object) {
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
