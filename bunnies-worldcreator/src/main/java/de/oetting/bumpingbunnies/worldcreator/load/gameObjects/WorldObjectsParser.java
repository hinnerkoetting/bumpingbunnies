package de.oetting.bumpingbunnies.worldcreator.load.gameObjects;

import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.worldcreator.load.ResourceProvider;
import de.oetting.bumpingbunnies.worldcreator.load.XmlReader;

public interface WorldObjectsParser {

	World build(ResourceProvider provider, XmlReader xmlReader);

}