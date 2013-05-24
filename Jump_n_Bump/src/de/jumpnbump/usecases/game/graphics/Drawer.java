package de.jumpnbump.usecases.game.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.World;

public class Drawer {

	private static final MyLog LOGGER = Logger.getLogger(Drawer.class);
	private final World world;

	public Drawer(World world) {
		this.world = world;
	}

	public void draw(Canvas canvas) {
		LOGGER.verbose("drawing...");
		canvas.drawColor(Color.WHITE);
		this.world.getPlayer1().draw(canvas);
	}
}
