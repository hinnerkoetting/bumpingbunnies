package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.model.game.objects.Player;

public interface PlayerDrawableFactory {

	Drawable create(Player player, int playerWidth, int playerHeight);

}
