package de.oetting.bumpingbunnies.pc.worldcreation.parser;

import de.oetting.bumpingbunnies.world.WorldProperties;
import de.oetting.bumpingbunnies.worldCreation.XmlRect;

public interface ObjectFactory<S> {

	S create(XmlRect rect, WorldProperties properties);
}
