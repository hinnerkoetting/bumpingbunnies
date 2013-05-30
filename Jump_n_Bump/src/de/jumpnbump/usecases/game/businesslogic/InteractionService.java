package de.jumpnbump.usecases.game.businesslogic;

import de.jumpnbump.usecases.game.model.GameObject;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public class InteractionService {

	private CollisionDetection collisionDetection;

	public InteractionService(CollisionDetection collisionDetection) {
		this.collisionDetection = collisionDetection;
	}

	public void interactWith(Player player, World world) {
		GameObject nextStep = player.simulateNextStep();
		for (GameObject object : world.getAllObjects()) {
			if (object.id() != player.id()) {
				interactWith(player, nextStep, object);
			}
		}
	}

	private void interactWith(Player player, GameObject nextStep,
			GameObject object) {

		if (this.collisionDetection.collidesWithRight(nextStep, object)) {
			if (player.getState().getMovementX() > 0) {
				player.getState().setMovementX(0);
			}
		}
		if (this.collisionDetection.collidesWithLeft(nextStep, object)) {
			if (player.getState().getMovementX() < 0) {
				player.getState().setMovementX(0);
			}
		}
		if (this.collisionDetection.collidesWithTop(nextStep, object)) {
			if (player.getState().getMovementY() > 0) {
				player.getState().setMovementY(0);
			}
		}
		if (this.collisionDetection.collidesWithBottom(nextStep, object)) {
			if (player.getState().getMovementY() < 0) {
				player.getState().setMovementY(0);
			}
		}
	}
}
