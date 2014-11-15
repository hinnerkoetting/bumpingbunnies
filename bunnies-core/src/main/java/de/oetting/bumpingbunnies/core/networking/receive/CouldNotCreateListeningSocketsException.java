package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.BunniesException;

public class CouldNotCreateListeningSocketsException extends BunniesException {

	public CouldNotCreateListeningSocketsException(String type, int port, Throwable cause) {
		super("Network error: Could not open " + type + " socket on port " + port + ". Is another server already running?", cause);
	}
}
