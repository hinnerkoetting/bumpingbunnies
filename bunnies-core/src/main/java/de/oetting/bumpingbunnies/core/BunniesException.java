package de.oetting.bumpingbunnies.core;

public class BunniesException extends RuntimeException {

	private final String userMessage;

	public BunniesException(String userMessage) {
		this.userMessage = userMessage;
	}

	public BunniesException(String userMessage, Throwable cause) {
		super(cause);
		this.userMessage = userMessage;
	}

	public BunniesException(Throwable cause) {
		super(cause);
		userMessage = "";
	}

	public BunniesException() {
		userMessage = "";
	}

	public String getUserMessage() {
		return userMessage;
	}

}
