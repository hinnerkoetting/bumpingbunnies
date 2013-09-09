package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.ObjectProvider;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class GameObjectInteractor {

	private final CollisionDetection collisionDetection;
	private final ObjectProvider objectProvider;

	public GameObjectInteractor(CollisionDetection collisionDetection, ObjectProvider objectProvider) {
		this.collisionDetection = collisionDetection;
		this.objectProvider = objectProvider;
	}

	public final void interactWith(Player player) {
		GameObject nextStep = player.simulateNextStep();
		// careful: next step is updated in interactWith if player collides with
		// objects
		interactWithPlayers(player, nextStep);
		interactWith(player, nextStep, this.objectProvider.getAllJumper());
		interactWith(player, nextStep, this.objectProvider.getAllWaters());
		interactWith(player, nextStep, this.objectProvider.getAllWalls());
		interactWith(player, nextStep, this.objectProvider.getAllIcyWalls());
	}

	private void interactWithPlayers(Player player, GameObject nextStep) {
		for (Player p : this.objectProvider.getAllPlayer()) {
			if (p.id() != player.id()) {
				interactWith(nextStep, player, p);
			}
		}
	}

	private void interactWith(Player player, GameObject nextStep, List<? extends GameObject> allJumper) {
		for (GameObject object : allJumper) {
			interactWith(nextStep, player, object);
		}

	}

	private void interactWith(GameObject nextStep, Player player,
			GameObject object) {
		if (this.collisionDetection.collides(nextStep, object)) {
			object.handleCollisionWithPlayer(player, this.collisionDetection);
		}
	}

}
