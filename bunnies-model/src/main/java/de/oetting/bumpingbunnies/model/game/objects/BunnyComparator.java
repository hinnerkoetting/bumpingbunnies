package de.oetting.bumpingbunnies.model.game.objects;

import java.util.Comparator;

public class BunnyComparator implements Comparator<Bunny> {

	@Override
	public int compare(Bunny o1, Bunny o2) {
		return Integer.compare(o1.id(), o2.id());
	}

}
