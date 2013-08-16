package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.usecases.game.ObjectProvider;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class InteractionService {

	private final CollisionDetection collisionDetection;
	private final ObjectProvider objectProvider;

	public InteractionService(CollisionDetection collisionDetection, ObjectProvider objectProvider) {
		this.collisionDetection = collisionDetection;
		this.objectProvider = objectProvider;
	}

	public final void interactWith(Player player) {
		GameObject nextStep = player.simulateNextStep();
		// careful: next step is updated in interactWith if player collides with
		// objects
		for (GameObject object : this.objectProvider.getAllObjects()) {
			interactWith(nextStep, player, object);
		}
		for (Player p : this.objectProvider.getAllPlayer()) {
			if (p.id() != player.id()) {
				interactWith(nextStep, player, p);
			}
		}
	}

	private void interactWith(GameObject nextStep, Player player,
			GameObject object) {
		if (this.collisionDetection.collides(nextStep, object)) {
			object.handleCollisionWithPlayer(player, this.collisionDetection);
		}
	}

}
