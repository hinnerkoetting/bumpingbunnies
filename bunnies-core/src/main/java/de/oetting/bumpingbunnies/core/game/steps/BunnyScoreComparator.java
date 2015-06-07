package de.oetting.bumpingbunnies.core.game.steps;

import java.util.Comparator;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class BunnyScoreComparator implements Comparator<Bunny> {

	@Override
	public int compare(Bunny o1, Bunny o2) {
		return o2.getScore() - o1.getScore();
	}
}
