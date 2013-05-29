package de.jumpnbump.usecases.game.android.input.PathFinder;

import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.PlayerState;

public class SimplePathFinder implements PathFinder {

	private Player player;

	public SimplePathFinder(Player player) {
		this.player = player;
	}

	@Override
	public boolean canBeReachedByJumping(double x, double y) {
		PlayerState state = this.player.getState();

		double diffY = state.getCenterY() - y;
		double diffX = Math.abs(state.getCenterX() - x);

		double absDiffY = Math.abs(diffY);
		boolean pointerIsOverPlayer = diffY > 0;
		return pointerIsOverPlayer && absDiffY > diffX;
	}

}
