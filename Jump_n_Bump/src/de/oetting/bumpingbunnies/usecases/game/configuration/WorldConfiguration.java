package de.oetting.bumpingbunnies.usecases.game.configuration;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.ClassicWorldBuilder;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.FirstWorldObjectsBuilder;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.SimpleObjectsBuilder;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.TestXmlWorldBuilder;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.WorldObjectsBuilder;

public enum WorldConfiguration {

	SIMPLE(SimpleObjectsBuilder.class), DEMO(FirstWorldObjectsBuilder.class), CLASSIC(
			ClassicWorldBuilder.class), TEST(TestXmlWorldBuilder.class);

	private Class<? extends WorldObjectsBuilder> factoryClass;

	private WorldConfiguration(Class<? extends WorldObjectsBuilder> clazz) {
		this.factoryClass = clazz;
	}

	public WorldObjectsBuilder createInputconfigurationClass(Context context) {
		try {
			return this.factoryClass.getConstructor().newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
