package de.oetting.bumpingbunnies.core.game.graphics.factory;

import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;

public interface GameObjectDrawableFactory {

	Drawable create(GameObjectWithImage object, int objectWidth, int objectHeight);

}
