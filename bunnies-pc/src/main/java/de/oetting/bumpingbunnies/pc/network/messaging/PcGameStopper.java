package de.oetting.bumpingbunnies.pc.network.messaging;

import javafx.application.Platform;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public class PcGameStopper implements ThreadErrorCallback {

	@Override
	public void onThreadError() {
		Platform.exit();
	}

}
