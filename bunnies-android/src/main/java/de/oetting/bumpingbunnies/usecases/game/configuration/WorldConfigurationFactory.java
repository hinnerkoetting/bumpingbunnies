package de.oetting.bumpingbunnies.usecases.game.configuration;

import de.oetting.bumpingbunnies.android.xml.parsing.CastleWorldbuilder;
import de.oetting.bumpingbunnies.android.xml.parsing.TestWorldBuilder;
import de.oetting.bumpingbunnies.android.xml.parsing.XmlClassicWorldBuilder;
import de.oetting.bumpingbunnies.core.worldCreation.parser.WorldObjectsParser;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;

public class WorldConfigurationFactory {

	public WorldObjectsParser createWorldParser(WorldConfiguration configuration) {
		switch (configuration) {
		case CASTLE:
			return new CastleWorldbuilder();
		case CLASSIC:
			return new XmlClassicWorldBuilder();
		case TEST:
			return new TestWorldBuilder();
		}
		throw new IllegalArgumentException(configuration.toString());
	}
}
