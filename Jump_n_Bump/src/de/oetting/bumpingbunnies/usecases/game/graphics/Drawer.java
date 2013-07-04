package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.logger.Logger;

public class Drawer {

	private static final Logger LOGGER = LoggerFactory.getLogger(Drawer.class);
	private List<Drawable> allDrawables;
	private DrawablesFactory factory;
	private CanvasDelegate canvasDelegate;
	private boolean needsUpdate;
	private int backgroundId;
	private Bitmap scaledBitmap;
	private final Context context;

	public Drawer(DrawablesFactory drawFactory, CanvasDelegate canvasDeleta,
			int backgroundId, Context context) {
		this.factory = drawFactory;
		this.context = context;
		this.allDrawables = new LinkedList<Drawable>();
		this.canvasDelegate = canvasDeleta;
		this.backgroundId = backgroundId;
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

		// this.canvasDelegate.drawColor(Color.WHITE);
		this.canvasDelegate.drawImageDirect(this.scaledBitmap, 0, 0, null);
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
			this.scaledBitmap = scaleBackground(canvas);

			for (Drawable d : this.allDrawables) {
				d.updateGraphics(this.canvasDelegate);
			}
			this.needsUpdate = false;
		}
	}

	private Bitmap scaleBackground(Canvas canvas) {
		Bitmap background = BitmapFactory.decodeResource(
				this.context.getResources(), this.backgroundId);
		return Bitmap.createScaledBitmap(background, canvas.getWidth(),
				canvas.getHeight(), false);
	}

	public void setNeedsUpdate(boolean b) {
		this.needsUpdate = b;
	}

}
