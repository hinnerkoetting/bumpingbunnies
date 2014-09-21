package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.Collection;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface DrawablesFactory {

	Collection<Drawable> createAllDrawables();

	Drawable createPlayerDrawable(Player p);

}
