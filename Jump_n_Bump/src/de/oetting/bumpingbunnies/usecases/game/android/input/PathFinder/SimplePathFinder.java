package de.oetting.bumpingbunnies.usecases.game.android.input.PathFinder;

import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public class SimplePathFinder implements PathFinder {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SimplePathFinder.class);
	private Player player;

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
