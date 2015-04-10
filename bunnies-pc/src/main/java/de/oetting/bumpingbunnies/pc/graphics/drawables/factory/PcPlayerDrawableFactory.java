package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.BunnyDrawableFactory;
import de.oetting.bumpingbunnies.core.game.graphics.BunnyDrawerFactory;
import de.oetting.bumpingbunnies.core.game.graphics.BunnyImagesReader;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class PcPlayerDrawableFactory implements BunnyDrawableFactory {

	private final BunnyDrawerFactory factory;

	public PcPlayerDrawableFactory() {
		this.factory = new BunnyDrawerFactory(new PcPlayerImagesProvider(new BunnyImagesReader()), new PcImagesColoror(), new PcImageMirroror());
	}

	@Override
	public Drawable create(Bunny player, int playerWidth, int playerHeight) {
		return factory.create(playerWidth, playerHeight, player);
	}
}
