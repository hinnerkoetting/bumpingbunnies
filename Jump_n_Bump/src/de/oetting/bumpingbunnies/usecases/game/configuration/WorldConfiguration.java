package de.oetting.bumpingbunnies.usecases.game.configuration;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.CastleWorldbuilder;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.FirstWorldObjectsBuilder;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.TestXmlWorldBuilder;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.WorldObjectsBuilder;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.XmlClassicWorldBuilder;

public enum WorldConfiguration {

	DEMO(FirstWorldObjectsBuilder.class), CLASSIC(
			XmlClassicWorldBuilder.class), TEST(TestXmlWorldBuilder.class), CASTLE(CastleWorldbuilder.class);

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
