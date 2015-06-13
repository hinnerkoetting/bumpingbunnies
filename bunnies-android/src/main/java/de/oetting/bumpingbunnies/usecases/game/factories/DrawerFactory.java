package de.oetting.bumpingbunnies.usecases.game.factories;

import android.content.Context;
import de.oetting.bumpingbunnies.core.game.graphics.BunnyDrawerFactory;
import de.oetting.bumpingbunnies.core.game.graphics.BunnyImagesReader;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasAdapter;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasCoordinateTranslator;
import de.oetting.bumpingbunnies.core.game.graphics.DrawablesFactory;
import de.oetting.bumpingbunnies.core.game.graphics.ImageMirroror;
import de.oetting.bumpingbunnies.core.game.graphics.ObjectsDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.ImagesColorer;
import de.oetting.bumpingbunnies.core.game.graphics.factory.PlayerImagesProvider;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.usecases.AndroidPlayerImagesProvider;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidCanvasAdapter;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidDrawableToImageConverter;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidImagesColoror;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidImagesMirrorer;

public class DrawerFactory {

	public static ObjectsDrawer create(World world, GameThreadState threadState, Configuration configuration,
			CoordinatesCalculation calculations, Context context) {

		BunnyDrawerFactory playerDrawerFactory = createPlayerDrawerFactory();
		CanvasAdapter canvasDelegate = new CanvasCoordinateTranslator(new AndroidCanvasAdapter(context), calculations);
		DrawablesFactory drawFactory = new DrawablesFactory(threadState, world, new AndroidBackgroundDrawableFactory(),
				new AndroidGameObjectsDrawableFactory(), playerDrawerFactory, new AndroidDrawableToImageConverter(
						canvasDelegate, calculations, context), true, false, calculations);

		calculations.setZoom(ModelConstants.STANDARD_WORLD_SIZE / ModelConstants.ZOOM_MULTIPLIER * configuration.getZoom());

		ObjectsDrawer drawer = new ObjectsDrawer(drawFactory, canvasDelegate);
		return drawer;
	}

	private static BunnyDrawerFactory createPlayerDrawerFactory() {
		BunnyImagesReader imagesReader = new BunnyImagesReader();
		PlayerImagesProvider imagesProvider = new AndroidPlayerImagesProvider(imagesReader);
		ImagesColorer colorer = new AndroidImagesColoror();
		ImageMirroror mirrorer = new AndroidImagesMirrorer();
		return new BunnyDrawerFactory(imagesProvider, colorer, mirrorer);
	}
}
