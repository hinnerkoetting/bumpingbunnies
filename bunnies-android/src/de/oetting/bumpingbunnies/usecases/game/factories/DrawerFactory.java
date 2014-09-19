package de.oetting.bumpingbunnies.usecases.game.factories;

import android.content.Context;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.graphics.CanvasDelegateImpl;
import de.oetting.bumpingbunnies.usecases.game.graphics.DrawablesFactory;
import de.oetting.bumpingbunnies.usecases.game.graphics.Drawer;
import de.oetting.bumpingbunnies.usecases.game.model.GameThreadState;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;

public class DrawerFactory {

	public static Drawer create(World world, GameThreadState threadState,
			Context context,
			Configuration configuration, CoordinatesCalculation calculations) {
		DrawablesFactory drawFactory = new DrawablesFactory(world, threadState,
				context.getResources(), configuration.getLocalSettings().isBackground());

		CanvasDelegateImpl canvasDelegate = new CanvasDelegateImpl(calculations);
		calculations.setZoom((ModelConstants.STANDARD_WORLD_SIZE / 7500 * configuration
				.getZoom()));

		Drawer drawer = new Drawer(drawFactory, canvasDelegate);
		drawer.buildAllDrawables();
		return drawer;
	}
}
