package de.oetting.bumpingbunnies.android.graphics;

import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;
import de.oetting.bumpingbunnies.core.game.graphics.ObjectsDrawer;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.core.graphics.Drawer;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidCanvasWrapper;

public class AndroidDrawer implements Drawer, SurfaceHolder.Callback {

	private static final Logger LOGGER = LoggerFactory.getLogger(AndroidDrawer.class);

	private SurfaceHolder holder;
	private final ObjectsDrawer objectsDrawer;
	private boolean isDrawingPossible;

	private boolean drawablesHaveChanged;

	public AndroidDrawer(ObjectsDrawer objectsDrawer) {
		super();
		this.objectsDrawer = objectsDrawer;
		drawablesHaveChanged = true;
	}

	@Override
	public void draw() {
		if (isDrawingPossible) {
			Canvas lockCanvas = this.holder.lockCanvas();
			if (lockCanvas != null) {
				synchronized (this.holder) {
					drawOnLockedCanvas(lockCanvas);
				}
			}
		}
	}

	private void drawOnLockedCanvas(Canvas lockCanvas) {
		try {
			CanvasWrapper canvas = new AndroidCanvasWrapper(lockCanvas);
			if (drawablesHaveChanged) {
				objectsDrawer.buildAllDrawables(canvas, lockCanvas.getWidth(), lockCanvas.getHeight());
				drawablesHaveChanged = false;
			}
			this.objectsDrawer.draw(canvas);
		} finally {
			this.holder.unlockCanvasAndPost(lockCanvas);
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.holder = holder;
		holder.setFormat(PixelFormat.RGBA_8888);
		this.isDrawingPossible = true;
		LOGGER.info("Surface created");
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		this.isDrawingPossible = false;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		this.holder = holder;
		drawablesHaveChanged = true;
	}

	@Override
	public void setNeedsUpdate(boolean b) {
		this.drawablesHaveChanged = b;
	}

	@Override
	public void newEvent(Bunny p) {
		objectsDrawer.newEvent(p);
	}

	@Override
	public void removeEvent(Bunny p) {
		objectsDrawer.removeEvent(p);
	}
}
