package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class BunnyDrawableFactory {

	private final BunnyDrawerFactory factory;

	public BunnyDrawableFactory(BunnyDrawerFactory factory) {
		this.factory = factory;
	}

	public Drawable create(Bunny player, int playerWidth, int playerHeight) {
		return factory.create(playerWidth, playerHeight, player);
	}

}
