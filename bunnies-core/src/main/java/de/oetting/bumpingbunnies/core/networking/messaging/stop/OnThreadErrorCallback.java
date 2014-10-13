package de.oetting.bumpingbunnies.core.networking.messaging.stop;

public interface OnThreadErrorCallback {

	void stopGame();

	void onDisconnect();
}
