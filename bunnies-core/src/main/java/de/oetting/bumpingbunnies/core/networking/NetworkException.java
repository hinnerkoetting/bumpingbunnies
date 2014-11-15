package de.oetting.bumpingbunnies.core.networking;

import de.oetting.bumpingbunnies.core.BunniesException;

public class NetworkException extends BunniesException {

	public NetworkException(String userMessage, Throwable cause) {
		super(userMessage, cause);
	}

	public NetworkException(Throwable cause) {
		super(cause);
	}

	public NetworkException(String userMessage) {
		super(userMessage);
	}

	public NetworkException() {
	}

}
