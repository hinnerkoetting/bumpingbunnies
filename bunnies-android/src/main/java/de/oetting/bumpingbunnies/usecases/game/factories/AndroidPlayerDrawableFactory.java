package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.PlayerDrawableFactory;
import de.oetting.bumpingbunnies.core.game.graphics.PlayerDrawerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class AndroidPlayerDrawableFactory implements PlayerDrawableFactory {

	private final PlayerDrawerFactory factory;

	public AndroidPlayerDrawableFactory(PlayerDrawerFactory factory) {
		this.factory = factory;
	}

	@Override
	public Drawable create(Bunny player, int playerWidth, int playerHeight) {
		return factory.create(playerWidth, playerHeight, player);
	}

}
