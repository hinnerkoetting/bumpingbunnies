package de.oetting.bumpingbunnies.usecases.game.factories;

import android.content.Context;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.AllPlayerConfig;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.graphics.CanvasDelegateImpl;
import de.oetting.bumpingbunnies.usecases.game.graphics.DrawablesFactory;
import de.oetting.bumpingbunnies.usecases.game.graphics.Drawer;
import de.oetting.bumpingbunnies.usecases.game.model.GameThreadState;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class DrawerFactory {

	public static Drawer create(World world, GameThreadState threadState,
			Context context, AllPlayerConfig playerConfig,
			Configuration configuration, CoordinatesCalculation calculations) {
		DrawablesFactory drawFactory = new DrawablesFactory(world, threadState,
				context.getResources());

		CanvasDelegateImpl canvasDelegate = new CanvasDelegateImpl(calculations);
		calculations.setZoom((ModelConstants.STANDARD_WORLD_SIZE / 7500 * configuration
				.getZoom()));

		Drawer drawer = new Drawer(drawFactory, canvasDelegate,
				R.drawable.hintergrund2, context, configuration.getLocalSettings().isBackground());
		drawer.buildAllDrawables();
		return drawer;
	}
}
