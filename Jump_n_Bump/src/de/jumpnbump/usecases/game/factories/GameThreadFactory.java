package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.GameThread;
import de.jumpnbump.usecases.game.GameThreadState;
import de.jumpnbump.usecases.game.WorldController;
import de.jumpnbump.usecases.game.graphics.Drawer;

public class GameThreadFactory {

	public static GameThread create(Drawer drawer,
			WorldController worldController, GameThreadState gameThreadState) {
		return new GameThread(drawer, worldController, gameThreadState);
	}
}
