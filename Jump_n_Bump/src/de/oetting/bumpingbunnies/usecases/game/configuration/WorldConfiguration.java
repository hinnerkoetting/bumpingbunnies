package de.oetting.bumpingbunnies.usecases.game.configuration;

import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.CastleWorldbuilder;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.TestWorldBuilder;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.WorldObjectsParser;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.XmlClassicWorldBuilder;

public enum WorldConfiguration {

	CLASSIC(
			XmlClassicWorldBuilder.class), CASTLE(CastleWorldbuilder.class), TEST(TestWorldBuilder.class);

	private Class<? extends WorldObjectsParser> factoryClass;

	private WorldConfiguration(Class<? extends WorldObjectsParser> clazz) {
		this.factoryClass = clazz;
	}

	public WorldObjectsParser createInputconfigurationClass() {
		try {
			return this.factoryClass.getConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
