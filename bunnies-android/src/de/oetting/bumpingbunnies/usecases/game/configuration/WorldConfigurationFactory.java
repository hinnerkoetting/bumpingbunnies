package de.oetting.bumpingbunnies.usecases.game.configuration;

import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.CastleWorldbuilder;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.TestWorldBuilder;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.WorldObjectsParser;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.XmlClassicWorldBuilder;

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
