package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.Collection;

import de.oetting.bumpingbunnies.model.game.objects.Player;

public interface DrawablesFactory {

	Collection<Drawable> createAllDrawables(CanvasDelegate canvas);

	Drawable createPlayerDrawable(Player p, CanvasDelegate canvas);

}
