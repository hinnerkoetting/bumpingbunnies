package de.oetting.bumpingbunnies.android.input.pathFinder;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PathFinderFactory {

	public static PathFinder createPathFinder(Player player) {
		return new SimplePathFinder(player);
	}
}
