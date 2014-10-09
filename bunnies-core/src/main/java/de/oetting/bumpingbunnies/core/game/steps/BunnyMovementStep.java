package de.oetting.bumpingbunnies.core.game.steps;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculation;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovementCalculationFactory;
import de.oetting.bumpingbunnies.model.game.objects.Player;

/**
 * Takes care that all bunnies are moved during each step of the game.
 * 
 */
public class BunnyMovementStep implements GameStepAction, PlayerJoinListener {

	private final List<PlayerMovementCalculation> playermovements;
	private final BunnyKillChecker killChecker;
	private final PlayerMovementCalculationFactory calculationFactory;

	public BunnyMovementStep(BunnyKillChecker killChecker, PlayerMovementCalculationFactory calculationFactory) {
		super();
		this.killChecker = killChecker;
		this.calculationFactory = calculationFactory;
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

	public void addAllJoinListeners(JoinObserver main) {
		main.addJoinListener(this.killChecker);
	}

	private PlayerMovementCalculation findPlayerMovementCalculation(Player p) {
		for (PlayerMovementCalculation c : this.playermovements) {
			if (c.controlsThisPlayer(p)) {
				return c;
			}
		}
		throw new PlayerDoesNotExist(p);
	}

	public static class PlayerDoesNotExist extends RuntimeException {

		public PlayerDoesNotExist(Player p) {
			super(p.toString());
		}
	}
}
