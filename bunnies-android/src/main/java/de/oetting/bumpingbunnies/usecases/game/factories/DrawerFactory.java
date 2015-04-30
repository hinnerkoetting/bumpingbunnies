package de.oetting.bumpingbunnies.usecases.game.factories;

import android.content.Context;
import de.oetting.bumpingbunnies.core.game.graphics.BunnyDrawableFactory;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasCoordinateTranslator;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.game.graphics.DrawablesFactory;
import de.oetting.bumpingbunnies.core.game.graphics.ImageMirroror;
import de.oetting.bumpingbunnies.core.game.graphics.ObjectsDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.BunnyDrawerFactory;
import de.oetting.bumpingbunnies.core.game.graphics.BunnyImagesReader;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.ImagesColorer;
import de.oetting.bumpingbunnies.core.game.graphics.factory.PlayerImagesProvider;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.usecases.AndroidPlayerImagesProvider;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidCanvasDelegate;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidImagesColoror;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidImagesMirrorer;

public class DrawerFactory {

	public static ObjectsDrawer create(World world, GameThreadState threadState, Configuration configuration, CoordinatesCalculation calculations, Context context) {

		BunnyDrawableFactory playerDrawerFactory = createPlayerDrawerFactory();
		DrawablesFactory drawFactory = new DrawablesFactory(threadState, world, new AndroidBackgroundDrawableFactory(configuration.getLocalSettings()
				.isBackground()), new AndroidGameObjectsDrawableFactory(), playerDrawerFactory, null);

		CanvasDelegate canvasDelegate = new CanvasCoordinateTranslator(new AndroidCanvasDelegate(context), calculations);
		calculations.setZoom(ModelConstants.STANDARD_WORLD_SIZE / 7500 * configuration.getZoom());

		ObjectsDrawer drawer = new ObjectsDrawer(drawFactory, canvasDelegate);
		return drawer;
	}

	private static BunnyDrawableFactory createPlayerDrawerFactory() {
		BunnyImagesReader imagesReader = new BunnyImagesReader();
		PlayerImagesProvider imagesProvider = new AndroidPlayerImagesProvider(imagesReader);
		ImagesColorer colorer = new AndroidImagesColoror();
		ImageMirroror mirrorer = new AndroidImagesMirrorer();
		return new BunnyDrawableFactory(new BunnyDrawerFactory(imagesProvider, colorer, mirrorer));
	}
}
