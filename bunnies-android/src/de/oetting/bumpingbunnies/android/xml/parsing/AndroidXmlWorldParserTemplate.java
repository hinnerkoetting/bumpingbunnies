package de.oetting.bumpingbunnies.android.xml.parsing;

import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.parser.WorldObjectsParser;
import de.oetting.bumpingbunnies.core.worldCreation.parser.XmlReader;

public class AndroidXmlWorldParserTemplate implements WorldObjectsParser {

	private WorldObjectsParser worldBuilder;

	public AndroidXmlWorldParserTemplate(int resourceId) {
		this.worldBuilder = new AndroidXmlWorldParser(resourceId);
	}

	@Override
	public World build(ResourceProvider provider, XmlReader reader) {
		return this.worldBuilder.build(provider, reader);
	}

	@Override
	public int getResourceId() {
		return worldBuilder.getResourceId();
	}

}