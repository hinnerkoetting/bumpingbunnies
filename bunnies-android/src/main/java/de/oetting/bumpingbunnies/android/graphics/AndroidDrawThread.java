package de.oetting.bumpingbunnies.android.graphics;

import de.oetting.bumpingbunnies.core.graphics.Drawer;
import de.oetting.bumpingbunnies.core.graphics.GameScreenSizeChangeListener;
import de.oetting.bumpingbunnies.core.threads.BunniesThread;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public class AndroidDrawThread extends BunniesThread implements GameScreenSizeChangeListener {

	private final Drawer drawer;
	private boolean canceled;

	public AndroidDrawThread(Drawer drawer, ThreadErrorCallback errorCallback) {
		super("Android drawer thread", errorCallback);
		this.drawer = drawer;
	}

	@Override
	protected void doRun() throws Exception {
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
