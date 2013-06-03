package de.jumpnbump.usecases.game.graphics;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.Color;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;

public class Drawer {

	private static final MyLog LOGGER = Logger.getLogger(Drawer.class);
	private List<Drawable> allDrawables;
	private DrawablesFactory factory;
	private CanvasDelegate canvasDelegate;
	private boolean wasUpdated;

	public Drawer(DrawablesFactory drawFactory, CanvasDelegate canvasDeleta) {
		this.factory = drawFactory;
		this.allDrawables = new LinkedList<Drawable>();
		this.canvasDelegate = canvasDeleta;
	}

	public void buildAllDrawables() {
		this.allDrawables.clear();
		this.allDrawables.addAll(this.factory.createAllDrawables());
		LOGGER.info("Added %d drawables", this.allDrawables.size());
	}

	public void draw(Canvas canvas) {
		LOGGER.verbose("drawing...");
		update();
		this.canvasDelegate.updateDelegate(canvas);

		this.canvasDelegate.drawColor(Color.WHITE);
		for (Drawable d : this.allDrawables) {
			d.draw(this.canvasDelegate);
		}
	}

	private void update() {
		if (!this.wasUpdated) {
			for (Drawable d : this.allDrawables) {
				d.updateGraphics(this.canvasDelegate);
				this.wasUpdated = true;
			}
		}
	}
}
