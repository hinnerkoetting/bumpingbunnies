package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import de.oetting.bumpingbunnies.core.game.movement.BunnyMovement;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class FixedPositionPlayerPosition extends BunnyMovement {

	private Bunny player;

	public FixedPositionPlayerPosition(Bunny movedPlayer) {
		super(movedPlayer, null, null, null);
		this.player = movedPlayer;
	}

	@Override
	public void nextStep(long delta) {
		this.player.setCenterX(100);
		this.player.setCenterY(100);
	}
}
