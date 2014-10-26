package de.oetting.bumpingbunnies.core.worldCreation.parser;

import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.core.world.World;

public interface WorldObjectsParser {

	World build(ResourceProvider provider, XmlReader xmlReader);

}