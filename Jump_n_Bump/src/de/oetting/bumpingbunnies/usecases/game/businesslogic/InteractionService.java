package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
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
		// careful: next step is updated in interactWith if player collides with
		// objects
		for (GameObject object : world.getAllObjects()) {

			interactWith(nextStep, player, object);
		}
		for (Player p : world.getAllPlayer()) {
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
