package de.jumpnbump.usecases.game.graphics;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;

public class Drawer {

	private static final MyLog LOGGER = Logger.getLogger(Drawer.class);
	private List<Drawable> allDrawables;
	private DrawablesFactory factory;

	private Paint linePaint = new Paint();

	public Drawer(DrawablesFactory drawFactory) {
		this.factory = drawFactory;
		this.allDrawables = new LinkedList<Drawable>();
		this.linePaint.setColor(Color.BLACK);
	}

	public void buildAllDrawables() {
		this.allDrawables.clear();
		this.allDrawables.addAll(this.factory.createAllDrawables());
		LOGGER.info("Added %d drawables", this.allDrawables.size());
	}

	public void draw(Canvas canvas) {
		LOGGER.verbose("drawing...");
		canvas.drawColor(Color.WHITE);
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		canvas.drawLine(0, height * 0.5f, width, height * 0.5f, this.linePaint);
		for (Drawable d : this.allDrawables) {
			d.draw(canvas);
		}
	}
}
