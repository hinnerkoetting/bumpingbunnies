package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.PlayerDrawableFactory;
import de.oetting.bumpingbunnies.core.game.graphics.PlayerDrawerFactory;
import de.oetting.bumpingbunnies.core.game.graphics.PlayerImagesReader;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class PcPlayerDrawableFactory implements PlayerDrawableFactory {

	private final PlayerDrawerFactory factory;

	public PcPlayerDrawableFactory() {
		this.factory = new PlayerDrawerFactory(new PcPlayerImagesProvider(new PlayerImagesReader()), new PcImagesColoror(), new PcImageMirroror());
	}

	@Override
	public Drawable create(Player player, int playerWidth, int playerHeight) {
		return factory.create(playerWidth, playerHeight, player);
	}
}
