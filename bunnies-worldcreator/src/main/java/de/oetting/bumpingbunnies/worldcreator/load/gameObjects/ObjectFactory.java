package de.oetting.bumpingbunnies.worldcreator.load.gameObjects;

import de.oetting.bumpingbunnies.model.game.world.WorldProperties;
import de.oetting.bumpingbunnies.model.game.world.XmlRect;

public interface ObjectFactory<S> {

	S create(XmlRect rect, WorldProperties properties);
}
