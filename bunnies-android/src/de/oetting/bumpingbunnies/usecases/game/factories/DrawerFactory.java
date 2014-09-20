package de.oetting.bumpingbunnies.usecases.game.factories;

import android.content.Context;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.graphics.CanvasDelegateImpl;
import de.oetting.bumpingbunnies.usecases.game.graphics.DrawablesFactory;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidObjectsDrawer;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;

public class DrawerFactory {

	public static AndroidObjectsDrawer create(World world, GameThreadState threadState,
			Context context,
			Configuration configuration, CoordinatesCalculation calculations) {
		DrawablesFactory drawFactory = new DrawablesFactory(world, threadState,
				context.getResources(), configuration.getLocalSettings().isBackground());

		CanvasDelegateImpl canvasDelegate = new CanvasDelegateImpl(calculations);
		calculations.setZoom((ModelConstants.STANDARD_WORLD_SIZE / 7500 * configuration
				.getZoom()));

		AndroidObjectsDrawer drawer = new AndroidObjectsDrawer(drawFactory, canvasDelegate);
		drawer.buildAllDrawables();
		return drawer;
	}
}
