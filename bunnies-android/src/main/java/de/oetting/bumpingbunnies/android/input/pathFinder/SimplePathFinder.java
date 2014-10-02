package de.oetting.bumpingbunnies.android.input.pathFinder;

import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public class SimplePathFinder implements PathFinder {

	private final Player player;

	public SimplePathFinder(Player player) {
		this.player = player;
	}

	@Override
	public boolean canBeReachedByJumping(double x, double y) {
		PlayerState state = this.player.getState();

		double diffY = state.getCenterY() - y;
		boolean pointerIsOverPlayer = diffY < 0;
		boolean isYDifferenceBiggerThanXDifference = true;// absDiffY > diffX;
		return pointerIsOverPlayer && isYDifferenceBiggerThanXDifference;
	}
}
