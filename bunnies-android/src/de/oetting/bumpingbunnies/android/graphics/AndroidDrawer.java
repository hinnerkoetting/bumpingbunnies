package de.oetting.bumpingbunnies.android.graphics;

import android.graphics.Canvas;
import android.graphics.PixelFormat;
import android.view.SurfaceHolder;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.core.graphics.Drawer;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidObjectsDrawer;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class AndroidDrawer implements Drawer, SurfaceHolder.Callback {

	private static final Logger LOGGER = LoggerFactory.getLogger(AndroidDrawer.class);

	private SurfaceHolder holder;
	private final AndroidObjectsDrawer objectsDrawer;
	private boolean isDrawingPossible;
	private final boolean altPixelMode;

	public AndroidDrawer(AndroidObjectsDrawer objectsDrawer, boolean altPixelMode) {
		super();
		this.objectsDrawer = objectsDrawer;
		this.altPixelMode = altPixelMode;
	}

	@Override
	public void draw() {
		if (isDrawingPossible) {
			Canvas lockCanvas = this.holder.lockCanvas();
			try {
				if (lockCanvas != null) {
					synchronized (this.holder) {
						this.objectsDrawer.draw(new CanvasWrapper(lockCanvas));
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
	}

	@Override
	public void setNeedsUpdate(boolean b) {
		objectsDrawer.setNeedsUpdate(b);
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
