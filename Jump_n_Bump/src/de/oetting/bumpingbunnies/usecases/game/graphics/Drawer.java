package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Canvas;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class Drawer {

	private static final Logger LOGGER = LoggerFactory.getLogger(Drawer.class);
	private List<Drawable> allDrawables;
	private DrawablesFactory factory;
	private CanvasDelegate canvasDelegate;
	private boolean needsUpdate;

	public Drawer(DrawablesFactory drawFactory, CanvasDelegate canvasDeleta) {
		this.factory = drawFactory;
		this.allDrawables = new LinkedList<Drawable>();
		this.canvasDelegate = canvasDeleta;
		this.needsUpdate = true;
	}

	public void buildAllDrawables() {
		this.allDrawables.clear();
		this.allDrawables.addAll(this.factory.createAllDrawables());
		LOGGER.info("Added %d drawables", this.allDrawables.size());
	}

	public void draw(Canvas canvas) {
		LOGGER.verbose("drawing...");
		update(canvas);
		drawEverything();
	}

	private void drawEverything() {
		for (Drawable d : this.allDrawables) {
			d.draw(this.canvasDelegate);
		}
	}

	private void update(Canvas canvas) {
		if (this.needsUpdate) {
			this.canvasDelegate.updateDelegate(canvas);

			for (Drawable d : this.allDrawables) {
				d.updateGraphics(this.canvasDelegate);
			}
			this.needsUpdate = false;
		}
	}

	public void setNeedsUpdate(boolean b) {
		this.needsUpdate = b;
	}

}
