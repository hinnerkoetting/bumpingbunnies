package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerJoinListener;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementCalculation;
import de.oetting.bumpingbunnies.usecases.game.factories.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

/**
 * Takes care that all bunnies are moved during each step of the game.
 * 
 */
public class BunnyMovementStep implements GameStepAction, PlayerJoinListener {

	private final List<PlayerMovementCalculation> playermovements;
	private final BunnyKillChecker killChecker;
	private final PlayerMovementCalculationFactory calculationFactory;

	public BunnyMovementStep(
			BunnyKillChecker killChecker, PlayerMovementCalculationFactory calculationFactory) {
		super();
		this.killChecker = killChecker;
		this.calculationFactory = calculationFactory;
		this.playermovements = new LinkedList<PlayerMovementCalculation>();
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		for (PlayerMovementCalculation movement : this.playermovements) {
			movement.nextStep(deltaStepsSinceLastCall);
		}
		this.killChecker.checkForJumpedPlayers();
		this.killChecker.checkForPlayerOutsideOfGameZone();
	}

	@Override
	public void newPlayerJoined(Player p) {
		PlayerMovementCalculation movementCalculation = this.calculationFactory.create(p);
		this.playermovements.add(movementCalculation);
	}

	@Override
	public void playerLeftTheGame(Player p) {
		PlayerMovementCalculation movementCalculation = findPlayerMovementCalculation(p);
		this.playermovements.remove(movementCalculation);
	}

	private PlayerMovementCalculation findPlayerMovementCalculation(Player p) {
		for (PlayerMovementCalculation c : this.playermovements) {
			if (c.controlsThisPlayer(p)) {
				return c;
			}
		}
		throw new PlayerDoesNotExist();
	}

	public class PlayerDoesNotExist extends RuntimeException {
	}
}
