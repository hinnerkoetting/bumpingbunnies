package de.oetting.bumpingbunnies.usecases.game.configuration;

import de.oetting.bumpingbunnies.android.xml.parsing.AndroidXmlWorldParserTemplate;
import de.oetting.bumpingbunnies.android.xml.parsing.CastleWorldbuilder;
import de.oetting.bumpingbunnies.android.xml.parsing.XmlClassicWorldBuilder;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;

public class WorldConfigurationFactory {

	public AndroidXmlWorldParserTemplate createWorldParser(WorldConfiguration configuration) {
		switch (configuration) {
		case CASTLE:
			return new CastleWorldbuilder();
		case CLASSIC:
			return new XmlClassicWorldBuilder();
		}
		throw new IllegalArgumentException(configuration.toString());
	}
}
