package de.oetting.bumpingbunnies.core.game.movement;

import java.util.List;

import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.objects.PlayerSimulation;
import de.oetting.bumpingbunnies.model.game.objects.Water;
import de.oetting.bumpingbunnies.model.game.world.ObjectProvider;

public class GameObjectInteractor {

	private final CollisionDetection collisionDetection;
	private final ObjectProvider objectProvider;
	private final CollisionHandling collisionHandling;

	public GameObjectInteractor(CollisionDetection collisionDetection, ObjectProvider objectProvider, CollisionHandling collisionHandling) {
		this.collisionDetection = collisionDetection;
		this.objectProvider = objectProvider;
		this.collisionHandling = collisionHandling;
	}

	public final void interactWith(Bunny player) {
		PlayerSimulation nextStep = player.simulateNextStep();
		// careful: next step is updated in interactWith if player collides with
		// objects
		interactWithPlayers(player, nextStep);
		interactWithJumper(player, nextStep, this.objectProvider.getCandidateForCollisionJumper(player));
		interactWithWater(player, nextStep, this.objectProvider.getCandidateForCollisionWater(player));
		interactWith(player, nextStep, this.objectProvider.getCandidateForCollisionWalls(player));
		interactWith(player, nextStep, this.objectProvider.getCandidateForCollisionIcyWalls(player));
	}

	private void interactWithJumper(Bunny player, PlayerSimulation nextStep, List<Jumper> allJumper) {
		for (Jumper object : allJumper) {
			if (this.collisionDetection.collides(nextStep, object)) {
				collisionHandling.interactWithJumper(player, object, collisionDetection);
			}
		}
	}

	private void interactWithWater(Bunny player, PlayerSimulation nextStep, List<Water> allWaters) {
		for (Water object : allWaters) {
			if (this.collisionDetection.collides(nextStep, object)) {
				collisionHandling.interactWithWater(nextStep, player, object, collisionDetection);
			}
		}
	}

	private void interactWithPlayers(Bunny player, PlayerSimulation nextStep) {
		for (Bunny otherBunny : this.objectProvider.getAllConnectedBunnies()) {
			if (otherBunny.id() != player.id()) 
				interactWithBunny(nextStep, player, otherBunny);
		}
	}

	private void interactWith(Bunny player, GameObject nextStep, List<? extends GameObject> allObjects) {
		for (GameObject object : allObjects) {
			interactWith(nextStep, player, object);
		}
	}

	private void interactWith(GameObject nextStep, Bunny player, GameObject object) {
		if (this.collisionDetection.collides(nextStep, object)) 
			collisionHandling.interactWith(player, object, collisionDetection);
	}
	
	private void interactWithBunny(GameObject nextStep, Bunny player,Bunny otherBunny) {
		if (this.collisionDetection.collides(nextStep, otherBunny)) 
			collisionHandling.interactWithBunnies(player, otherBunny, collisionDetection);
	}

}
