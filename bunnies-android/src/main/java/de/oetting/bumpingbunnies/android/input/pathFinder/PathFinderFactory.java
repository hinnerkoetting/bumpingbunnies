package de.oetting.bumpingbunnies.android.input.pathFinder;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class PathFinderFactory {

	public static PathFinder createPathFinder(Bunny player) {
		return new SimplePathFinder(player);
	}
}
