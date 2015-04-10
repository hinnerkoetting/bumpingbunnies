package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.BunnyDrawableFactory;
import de.oetting.bumpingbunnies.core.game.graphics.BunnyDrawerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class AndroidPlayerDrawableFactory implements BunnyDrawableFactory {

	private final BunnyDrawerFactory factory;

	public AndroidPlayerDrawableFactory(BunnyDrawerFactory factory) {
		this.factory = factory;
	}

	@Override
	public Drawable create(Bunny player, int playerWidth, int playerHeight) {
		return factory.create(playerWidth, playerHeight, player);
	}

}
