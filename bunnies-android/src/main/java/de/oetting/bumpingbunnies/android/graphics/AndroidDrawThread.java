package de.oetting.bumpingbunnies.android.graphics;

import de.oetting.bumpingbunnies.core.graphics.Drawer;
import de.oetting.bumpingbunnies.core.graphics.GameScreenSizeChangeListener;

public class AndroidDrawThread extends Thread implements GameScreenSizeChangeListener {

	private final Drawer drawer;
	private boolean canceled;

	public AndroidDrawThread(Drawer drawer) {
		super();
		this.drawer = drawer;
	}

	@Override
	public void run() {
		while (!canceled) {
			drawer.draw();
		}
	}

	public void cancel() {
		canceled = true;
	}

	@Override
	public void setNewSize(int width, int height) {
		this.drawer.setNeedsUpdate(true);
	}

}
