package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.GameThreadState;
import de.jumpnbump.usecases.game.graphics.DrawablesFactory;
import de.jumpnbump.usecases.game.graphics.Drawer;
import de.jumpnbump.usecases.game.model.World;

public class DrawerFactory {

	public static Drawer create(World world, GameThreadState threadState) {
		DrawablesFactory drawFactory = new DrawablesFactory(world, threadState);
		Drawer drawer = new Drawer(drawFactory);
		return drawer;
	}
}
