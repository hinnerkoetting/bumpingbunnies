package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class BunnyDrawableFactory {

	private static final double FACTOR_BY_WHICH_BUNNY_IS_BIGGER_THAN_HITBOX = 1.25;
	private final BunnyDrawerFactory factory;

	public BunnyDrawableFactory(BunnyDrawerFactory factory) {
		this.factory = factory;
	}

	public Drawable create(Bunny player, int playerWidth, int playerHeight) {
		return factory.create((int) (playerWidth * getFactor()), (int) (playerHeight * getFactor()), player);
	}

	private double getFactor() {
		return FACTOR_BY_WHICH_BUNNY_IS_BIGGER_THAN_HITBOX;
	}

}
