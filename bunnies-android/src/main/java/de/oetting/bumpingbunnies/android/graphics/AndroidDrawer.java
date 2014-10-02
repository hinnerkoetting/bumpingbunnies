package de.oetting.bumpingbunnies.android.graphics;

import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;
import de.oetting.bumpingbunnies.core.game.graphics.ObjectsDrawer;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.core.graphics.Drawer;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidCanvasWrapper;

public class AndroidDrawer implements Drawer, SurfaceHolder.Callback {

	private static final Logger LOGGER = LoggerFactory.getLogger(AndroidDrawer.class);

	private SurfaceHolder holder;
	private final ObjectsDrawer objectsDrawer;
	private boolean isDrawingPossible;
	private final boolean altPixelMode;

	private boolean needsUpdate;

	public AndroidDrawer(ObjectsDrawer objectsDrawer, boolean altPixelMode) {
		super();
		this.objectsDrawer = objectsDrawer;
		this.altPixelMode = altPixelMode;
		needsUpdate = true;
	}

	@Override
	public void draw() {
		if (isDrawingPossible) {
			Canvas lockCanvas = this.holder.lockCanvas();
			CanvasWrapper canvas = new AndroidCanvasWrapper(lockCanvas);
			try {
				if (lockCanvas != null) {
					synchronized (this.holder) {
						if (needsUpdate) {
							objectsDrawer.buildAllDrawables(canvas, lockCanvas.getWidth(), lockCanvas.getHeight());
							needsUpdate = false;
						}
						this.objectsDrawer.draw(canvas);
					}
				}
			} finally {
				if (lockCanvas != null) {
					this.holder.unlockCanvasAndPost(lockCanvas);
				}
			}
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.holder = holder;
		if (this.altPixelMode) {
			holder.setFormat(PixelFormat.OPAQUE);
		} else {
			holder.setFormat(PixelFormat.RGBA_8888);
		}
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
		needsUpdate = true;
	}

	@Override
	public void setNeedsUpdate(boolean b) {
		this.needsUpdate = b;
	}

	@Override
	public void newPlayerJoined(Player p) {
		objectsDrawer.newPlayerJoined(p);
	}

	@Override
	public void playerLeftTheGame(Player p) {
		objectsDrawer.playerLeftTheGame(p);
	}

}
