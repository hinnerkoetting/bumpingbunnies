package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface PlayerDrawableFactory {

	Drawable create(Player player, int playerWidth, int playerHeight);

}
