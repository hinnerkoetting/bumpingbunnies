package de.jumpnbump.usecases.game.factories;

import android.content.res.Resources;
import de.jumpnbump.usecases.game.android.calculation.CoordinatesCalculation;
import de.jumpnbump.usecases.game.businesslogic.PlayerConfig;
import de.jumpnbump.usecases.game.configuration.Configuration;
import de.jumpnbump.usecases.game.graphics.CanvasDelegateImpl;
import de.jumpnbump.usecases.game.graphics.DrawablesFactory;
import de.jumpnbump.usecases.game.graphics.Drawer;
import de.jumpnbump.usecases.game.model.GameThreadState;
import de.jumpnbump.usecases.game.model.ModelConstants;
import de.jumpnbump.usecases.game.model.World;

public class DrawerFactory {

	public static Drawer create(World world, GameThreadState threadState,
			Resources resources, PlayerConfig playerConfig,
			Configuration configuration, CoordinatesCalculation calculations) {
		DrawablesFactory drawFactory = new DrawablesFactory(world, threadState,
				resources);

		CanvasDelegateImpl canvasDelegate = new CanvasDelegateImpl(calculations);
		calculations.setZoom((ModelConstants.MAX_VALUE / 7500 * configuration
				.getZoom()));
		Drawer drawer = new Drawer(drawFactory, canvasDelegate);
		drawer.buildAllDrawables();
		return drawer;
	}
}
