package de.jumpnbump.usecases.game.configuration;

import de.jumpnbump.usecases.game.model.worldfactory.ClassicJumpnBumpWorldBuilder;
import de.jumpnbump.usecases.game.model.worldfactory.FirstWorldObjectsBuilder;
import de.jumpnbump.usecases.game.model.worldfactory.SimpleObjectsBuilder;
import de.jumpnbump.usecases.game.model.worldfactory.WorldObjectsBuilder;

public enum WorldConfiguration {

	SIMPLE(SimpleObjectsBuilder.class), DEMO(FirstWorldObjectsBuilder.class), CLASSIC(
			ClassicJumpnBumpWorldBuilder.class);

	private Class<? extends WorldObjectsBuilder> factoryClass;

	private WorldConfiguration(Class<? extends WorldObjectsBuilder> clazz) {
		this.factoryClass = clazz;
	}

	public WorldObjectsBuilder createInputconfigurationClass() {
		try {
			return this.factoryClass.getConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
