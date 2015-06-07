package de.oetting.bumpingbunnies.core.game.steps;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.game.movement.BunnyMovement;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.world.PlayerDoesNotExist;

/**
 * Takes care that all bunnies are moved during each step of the game.
 * 
 */
public class BunnyMovementStep implements GameStepAction, PlayerJoinListener {

	private final List<BunnyMovement> playermovements;
	private final BunnyKillChecker killChecker;
	private final PlayerMovementCalculationFactory calculationFactory;
	private final FixPlayerPosition fixPlayerPosition;

	public BunnyMovementStep(BunnyKillChecker killChecker, PlayerMovementCalculationFactory calculationFactory, FixPlayerPosition fixPlayerPosition) {
		this.killChecker = killChecker;
		this.calculationFactory = calculationFactory;
		this.fixPlayerPosition = fixPlayerPosition;
		this.playermovements = new CopyOnWriteArrayList<BunnyMovement>();
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		for (BunnyMovement movement : this.playermovements) {
			movement.nextStep(deltaStepsSinceLastCall);
		}
		this.killChecker.checkForJumpedPlayers();
		//move them backwards so that they do not overlap
		//Checking this at this time seems to be the best place. If this check was done before it sometimes happened that the player moved backwards through blocks.
		fixPlayerPosition.movePlayerBackwards();
		this.killChecker.checkForPlayerOutsideOfGameZone();
	}

	@Override
	public void newEvent(Bunny p) {
		BunnyMovement movementCalculation = this.calculationFactory.create(p);
		this.playermovements.add(movementCalculation);
		fixPlayerPosition.newEvent(p);
	}

	@Override
	public void removeEvent(Bunny p) {
		BunnyMovement movementCalculation = findPlayerMovementCalculation(p);
		this.playermovements.remove(movementCalculation);
		fixPlayerPosition.removeEvent(p);
	}

	public void addAllJoinListeners(JoinObserver main) {
		main.addJoinListener(this.killChecker);
		killChecker.addJoinListener(main);
	}

	private BunnyMovement findPlayerMovementCalculation(Bunny p) {
		for (BunnyMovement c : this.playermovements) {
			if (c.controlsThisPlayer(p)) {
				return c;
			}
		}
		throw new PlayerDoesNotExist(p.id());
	}

}
