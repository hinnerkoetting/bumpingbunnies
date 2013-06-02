package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.graphics.CanvasAroundPlayerDelegate;
import de.jumpnbump.usecases.game.graphics.DrawablesFactory;
import de.jumpnbump.usecases.game.graphics.Drawer;
import de.jumpnbump.usecases.game.model.GameThreadState;
import de.jumpnbump.usecases.game.model.ModelConstants;
import de.jumpnbump.usecases.game.model.World;

public class DrawerFactory {

	public static Drawer create(World world, GameThreadState threadState) {
		DrawablesFactory drawFactory = new DrawablesFactory(world, threadState);
		CanvasAroundPlayerDelegate canvasDelegate = new CanvasAroundPlayerDelegate(
				world.getPlayer1());
		canvasDelegate.setZoom((ModelConstants.MAX_VALUE / 2500));
		Drawer drawer = new Drawer(drawFactory, canvasDelegate);
		drawer.buildAllDrawables();
		return drawer;
	}
}
