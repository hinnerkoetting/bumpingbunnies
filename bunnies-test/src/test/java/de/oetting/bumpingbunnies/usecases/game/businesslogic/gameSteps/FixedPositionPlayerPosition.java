package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class FixedPositionPlayerPosition extends PlayerMovement {

	private Player player;

	public FixedPositionPlayerPosition(Player movedPlayer) {
		super(movedPlayer, null, null, null);
		this.player = movedPlayer;
	}

	@Override
	public void nextStep(long delta) {
		this.player.setCenterX(100);
		this.player.setCenterY(100);
	}
}
