package de.jumpnbump.usecases.game.graphics;

import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;

public class Drawer {

	private static final MyLog LOGGER = Logger.getLogger(Drawer.class);
	private List<Drawable> allDrawables;

	public Drawer(DrawablesFactory drawFactory) {
		this.allDrawables = drawFactory.createAllDrawables();
	}

	public void draw(Canvas canvas) {
		LOGGER.verbose("drawing...");
		canvas.drawColor(Color.WHITE);
		for (Drawable d : this.allDrawables) {
			d.draw(canvas);
		}
	}
}
