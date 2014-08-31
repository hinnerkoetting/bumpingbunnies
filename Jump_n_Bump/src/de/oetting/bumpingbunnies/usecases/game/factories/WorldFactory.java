package de.oetting.bumpingbunnies.usecases.game.factories;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.WorldObjectsBuilder;

public class WorldFactory {

	public static World create(Configuration configuration, Context context) {
		WorldObjectsBuilder factory = configuration.getWorldConfiguration().createInputconfigurationClass();
		World world = new World();
		factory.build(context);
		return world;
	}
}
