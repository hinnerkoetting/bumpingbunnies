package de.oetting.bumpingbunnies.core.game.graphics.factory;

import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;

public interface GameObjectDrawableFactory {

	Drawable create(GameObject object, int objectWidth, int objectHeight);

}
