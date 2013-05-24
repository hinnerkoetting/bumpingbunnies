package de.jumpnbump.usecases.game.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.GameThreadState;
import de.jumpnbump.usecases.game.model.World;

public class Drawer {

	private static final MyLog LOGGER = Logger.getLogger(Drawer.class);
	private final World world;
	private final GameThreadState threadState;

	public Drawer(World world, GameThreadState threadState) {
		this.world = world;
		this.threadState = threadState;
	}

	public void draw(Canvas canvas) {
		LOGGER.verbose("drawing...");
		canvas.drawColor(Color.WHITE);
		this.threadState.drawFps(canvas);
		this.world.getPlayer1().draw(canvas);
	}
}
