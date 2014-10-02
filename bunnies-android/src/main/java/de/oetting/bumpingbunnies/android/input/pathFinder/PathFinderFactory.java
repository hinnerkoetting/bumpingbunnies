package de.oetting.bumpingbunnies.android.input.pathFinder;

import de.oetting.bumpingbunnies.model.game.objects.Player;

public class PathFinderFactory {

	public static PathFinder createPathFinder(Player player) {
		return new SimplePathFinder(player);
	}
}
