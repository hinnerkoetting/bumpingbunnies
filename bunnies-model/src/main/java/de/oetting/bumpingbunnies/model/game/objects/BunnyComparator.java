package de.oetting.bumpingbunnies.model.game.objects;

import java.util.Comparator;

import de.oetting.bumpingbunnies.IntegerComparator;

public class BunnyComparator implements Comparator<Bunny> {

	@Override
	public int compare(Bunny o1, Bunny o2) {
		return IntegerComparator.compareInt(o1.id(), o2.id());
	}

}
