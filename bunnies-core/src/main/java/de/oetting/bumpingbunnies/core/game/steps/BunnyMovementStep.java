package de.oetting.bumpingbunnies.core.game.steps;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculation;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.core.world.PlayerDoesNotExist;
import de.oetting.bumpingbunnies.model.game.objects.Player;

/**
 * Takes care that all bunnies are moved during each step of the game.
 * 
 */
public class BunnyMovementStep implements GameStepAction, PlayerJoinListener {

	private final List<PlayerMovementCalculation> playermovements;
	private final BunnyKillChecker killChecker;
	private final PlayerMovementCalculationFactory calculationFactory;
	private final FixPlayerPosition fixPlayerPosition;

	public BunnyMovementStep(BunnyKillChecker killChecker, PlayerMovementCalculationFactory calculationFactory, FixPlayerPosition fixPlayerPosition) {
		this.killChecker = killChecker;
		this.calculationFactory = calculationFactory;
		this.fixPlayerPosition = fixPlayerPosition;
		this.playermovements = new CopyOnWriteArrayList<PlayerMovementCalculation>();
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		for (PlayerMovementCalculation movement : this.playermovements) {
			movement.nextStep(deltaStepsSinceLastCall);
			// must be in this line otherwise kill checks will not work
			// properly. the other player might move a bit and the bunny might
			// not be exactly on top of
			// the other
			this.killChecker.checkForJumpedPlayers();
		}
		//move them backwards so that they do not overlawp
		//Checking this at this time seems to be the best place. If this check was done before it sometimes happened that the player moved backwards through blocks.
		fixPlayerPosition.movePlayerBackwards();
		this.killChecker.checkForPlayerOutsideOfGameZone();
	}

	@Override
	public void newEvent(Player p) {
		PlayerMovementCalculation movementCalculation = this.calculationFactory.create(p);
		this.playermovements.add(movementCalculation);
		fixPlayerPosition.newEvent(p);
	}

	@Override
	public void removeEvent(Player p) {
		PlayerMovementCalculation movementCalculation = findPlayerMovementCalculation(p);
		this.playermovements.remove(movementCalculation);
		fixPlayerPosition.removeEvent(p);
	}

	public void addAllJoinListeners(JoinObserver main) {
		main.addJoinListener(this.killChecker);
	}

	private PlayerMovementCalculation findPlayerMovementCalculation(Player p) {
		for (PlayerMovementCalculation c : this.playermovements) {
			if (c.controlsThisPlayer(p)) {
				return c;
			}
		}
		throw new PlayerDoesNotExist(p.id());
	}

}
