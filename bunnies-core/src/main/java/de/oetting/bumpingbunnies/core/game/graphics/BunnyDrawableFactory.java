package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public interface BunnyDrawableFactory {

	Drawable create(Bunny player, int playerWidth, int playerHeight);

}
