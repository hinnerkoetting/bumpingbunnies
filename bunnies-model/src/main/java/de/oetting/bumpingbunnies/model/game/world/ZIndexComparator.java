package de.oetting.bumpingbunnies.model.game.world;

import java.util.Comparator;

import de.oetting.bumpingbunnies.IntegerComparator;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;

public class ZIndexComparator implements Comparator<GameObjectWithImage> {

	@Override
	public int compare(GameObjectWithImage o1, GameObjectWithImage o2) {
		return IntegerComparator.compareInt(o1.getzIndex(), o2.getzIndex());
	}

}
