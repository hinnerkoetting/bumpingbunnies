package de.oetting.bumpingbunnies.core.threads;

public interface ThreadErrorCallback {

	/**
	 * Gets called when an error an a thread happens. The usual response would
	 * be to notifiy the user and quit the game.
	 */
	void onThreadError();
	
	void onInitializationError(String message);

}