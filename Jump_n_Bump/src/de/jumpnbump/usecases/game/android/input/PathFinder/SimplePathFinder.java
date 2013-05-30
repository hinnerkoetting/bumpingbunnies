package de.jumpnbump.usecases.game.android.input.PathFinder;

import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.PlayerState;

public class SimplePathFinder implements PathFinder {

	private static final MyLog LOGGER = Logger
			.getLogger(SimplePathFinder.class);
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
		boolean pointerIsOverPlayer = diffY < 0;
		boolean isYDifferenceBiggerThanXDifference = true;// absDiffY > diffX;
		return pointerIsOverPlayer && isYDifferenceBiggerThanXDifference;
	}
}
