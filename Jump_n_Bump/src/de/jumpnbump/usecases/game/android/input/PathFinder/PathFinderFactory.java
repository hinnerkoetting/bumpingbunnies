package de.jumpnbump.usecases.game.android.input.PathFinder;

import de.jumpnbump.usecases.game.model.Player;

public class PathFinderFactory {

	public static PathFinder createPathFinder(Player player) {
		return new SimplePathFinder(player);
	}
}
