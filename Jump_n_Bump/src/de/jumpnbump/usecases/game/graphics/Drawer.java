package de.jumpnbump.usecases.game.graphics;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Bitmap;
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
	private Bitmap originalBackground;
	private Bitmap scaledBitmap;

	public Drawer(DrawablesFactory drawFactory, CanvasDelegate canvasDeleta,
			Bitmap background) {
		this.factory = drawFactory;
		this.allDrawables = new LinkedList<Drawable>();
		this.canvasDelegate = canvasDeleta;
		this.originalBackground = background;
	}

	public void buildAllDrawables() {
		this.allDrawables.clear();
		this.allDrawables.addAll(this.factory.createAllDrawables());
		LOGGER.info("Added %d drawables", this.allDrawables.size());
	}

	public void draw(Canvas canvas) {
		LOGGER.verbose("drawing...");
		update(canvas);
		this.canvasDelegate.updateDelegate(canvas);

		this.canvasDelegate.drawColor(Color.WHITE);
		this.canvasDelegate.drawImageDirect(this.scaledBitmap, 0, 0, null);
		drawEverything();
	}

	private void drawEverything() {
		synchronized (this.allDrawables) {
			for (Drawable d : this.allDrawables) {
				d.draw(this.canvasDelegate);
			}
		}
	}

	private void update(Canvas canvas) {

		if (!this.wasUpdated) {
			this.scaledBitmap = Bitmap.createScaledBitmap(
					this.originalBackground, canvas.getWidth(),
					canvas.getHeight(), false);
			for (Drawable d : this.allDrawables) {
				d.updateGraphics(this.canvasDelegate);
				this.wasUpdated = true;
			}
		}
	}

	public void addNewDrawable(RectDrawer rectDrawer) {
		synchronized (this.allDrawables) {
			this.allDrawables.add(rectDrawer);
		}
	}
}
