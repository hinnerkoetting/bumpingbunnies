package de.oetting.bumpingbunnies.core.game.movement;

import java.util.List;

import de.oetting.bumpingbunnies.core.world.ObjectProvider;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.game.objects.PlayerSimulation;
import de.oetting.bumpingbunnies.model.game.objects.Water;

public class GameObjectInteractor {

	private final CollisionDetection collisionDetection;
	private final ObjectProvider objectProvider;
	private final CollisionHandling collisionHandling;

	public GameObjectInteractor(CollisionDetection collisionDetection, ObjectProvider objectProvider, CollisionHandling collisionHandling) {
		this.collisionDetection = collisionDetection;
		this.objectProvider = objectProvider;
		this.collisionHandling = collisionHandling;
	}

	public final void interactWith(Player player) {
		PlayerSimulation nextStep = player.simulateNextStep();
		// careful: next step is updated in interactWith if player collides with
		// objects
		interactWithPlayers(player, nextStep);
		interactWith(player, nextStep, this.objectProvider.getAllJumper());
		interactWithWater(player, nextStep, this.objectProvider.getAllWaters());
		interactWith(player, nextStep, this.objectProvider.getAllWalls());
		interactWith(player, nextStep, this.objectProvider.getAllIcyWalls());
	}

	private void interactWithWater(Player player, PlayerSimulation nextStep, List<Water> allWaters) {
		for (Water object : allWaters) {
			if (this.collisionDetection.collides(nextStep, object)) {
				collisionHandling.interactWithWater(nextStep, player, object, collisionDetection);
			}
		}
	}

	private void interactWithPlayers(Player player, PlayerSimulation nextStep) {
		for (Player p : this.objectProvider.getAllPlayer()) {
			if (p.id() != player.id()) {
				interactWith(nextStep, player, p);
			}
		}
	}

	private void interactWith(Player player, GameObject nextStep, List<? extends GameObject> allObjects) {
		for (GameObject object : allObjects) {
			interactWith(nextStep, player, object);
		}
	}

	private void interactWith(GameObject nextStep, Player player, GameObject object) {
		if (this.collisionDetection.collides(nextStep, object)) {
			collisionHandling.interactWith(player, object, collisionDetection);
		}
	}

}
