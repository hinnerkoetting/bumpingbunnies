package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovementCalculation;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class FixedPositionPlayerMovementCalculation extends PlayerMovementCalculation {

	private Player player;

	public FixedPositionPlayerMovementCalculation(Player movedPlayer) {
		super(movedPlayer, null, null, null);
		this.player = movedPlayer;
	}

	@Override
	public void nextStep(long delta) {
		this.player.setCenterX(100);
		this.player.setCenterY(100);
	}
}
