package de.oetting.bumpingbunnies.android.xml.parsing;

import android.content.Context;
import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.core.worldCreation.parser.WorldObjectsParser;

public class AndroidXmlWorldParserTemplate {

	private final WorldObjectsParser worldBuilder;
	private final int resourceId;

	public AndroidXmlWorldParserTemplate(int resourceId) {
		this.resourceId = resourceId;
		this.worldBuilder = new AndroidXmlWorldParser();
	}

	public World build(ResourceProvider provider, Context context) {
		return this.worldBuilder.build(provider, new AndroidXmlReader(context, resourceId));
	}

}