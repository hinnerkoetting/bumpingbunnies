package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.core.game.graphics.CanvasCoordinateTranslator;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.game.graphics.DrawablesFactory;
import de.oetting.bumpingbunnies.core.game.graphics.ImageMirroror;
import de.oetting.bumpingbunnies.core.game.graphics.ObjectsDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.PlayerDrawerFactory;
import de.oetting.bumpingbunnies.core.game.graphics.PlayerImagesReader;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.ImagesColorer;
import de.oetting.bumpingbunnies.core.game.graphics.factory.PlayerImagesProvider;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.usecases.AndroidPlayerImagesProvier;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidCanvasDelegate;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidImagesColoror;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidImagesMirrorer;

public class DrawerFactory {

	public static ObjectsDrawer create(World world, GameThreadState threadState, Configuration configuration, CoordinatesCalculation calculations) {

		AndroidPlayerDrawableFactory playerDrawerFactory = createPlayerDrawerFactory();
		DrawablesFactory drawFactory = new DrawablesFactory(threadState, world, new AndroidBackgroundDrawableFactory(configuration.getLocalSettings()
				.isBackground()), new AndroidGameObjectsDrawableFactory(), playerDrawerFactory);

		CanvasDelegate canvasDelegate = new CanvasCoordinateTranslator(new AndroidCanvasDelegate(), calculations);
		calculations.setZoom((ModelConstants.STANDARD_WORLD_SIZE / 7500 * configuration.getZoom()));

		ObjectsDrawer drawer = new ObjectsDrawer(drawFactory, canvasDelegate);
		return drawer;
	}

	private static AndroidPlayerDrawableFactory createPlayerDrawerFactory() {
		PlayerImagesReader imagesReader = new PlayerImagesReader();
		PlayerImagesProvider imagesProvider = new AndroidPlayerImagesProvier(imagesReader);
		ImagesColorer colorer = new AndroidImagesColoror();
		ImageMirroror mirrorer = new AndroidImagesMirrorer();
		return new AndroidPlayerDrawableFactory(new PlayerDrawerFactory(imagesProvider, colorer, mirrorer));
	}
}
