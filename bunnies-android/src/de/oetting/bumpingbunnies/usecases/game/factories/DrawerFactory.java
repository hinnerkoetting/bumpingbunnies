package de.oetting.bumpingbunnies.usecases.game.factories;

import android.content.Context;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasCoordinateTranslator;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.game.graphics.ObjectsDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidCanvasDelegate;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidDrawablesFactory;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;

public class DrawerFactory {

	public static ObjectsDrawer create(World world, GameThreadState threadState, Context context, Configuration configuration,
			CoordinatesCalculation calculations) {
		AndroidDrawablesFactory drawFactory = new AndroidDrawablesFactory(world, threadState, context.getResources(), configuration.getLocalSettings()
				.isBackground());

		CanvasDelegate canvasDelegate = new CanvasCoordinateTranslator(new AndroidCanvasDelegate(), calculations);
		calculations.setZoom((ModelConstants.STANDARD_WORLD_SIZE / 7500 * configuration.getZoom()));

		ObjectsDrawer drawer = new ObjectsDrawer(drawFactory, canvasDelegate);
		return drawer;
	}
}
