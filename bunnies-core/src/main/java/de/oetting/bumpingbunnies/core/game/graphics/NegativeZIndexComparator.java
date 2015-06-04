package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.Comparator;

import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.world.ZIndexComparator;

public class NegativeZIndexComparator implements Comparator<GameObjectWithImage> {

	private final ZIndexComparator delegate = new ZIndexComparator();

	public int compare(GameObjectWithImage o1, GameObjectWithImage o2) {
		return - delegate.compare(o1, o2);
	}
	
}
