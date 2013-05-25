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
	private RectDrawer player1;
	private RectDrawer player2;

	public Drawer(World world, GameThreadState threadState) {
		this.world = world;
		this.threadState = threadState;
		this.player1 = new RectDrawer(world.getPlayer1());
		this.player2 = new RectDrawer(world.getPlayer2());
	}

	public void draw(Canvas canvas) {
		LOGGER.verbose("drawing...");
		canvas.drawColor(Color.WHITE);
		this.threadState.drawFps(canvas);
		this.player1.draw(canvas);
		this.player2.draw(canvas);
	}
}
