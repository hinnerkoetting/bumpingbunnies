package de.oetting.bumpingbunnies.usecases.game.communication.objects;

public class JsonWrapper {

	private final MessageId id;
	private final String message;

	public JsonWrapper(MessageId id, String message) {
		this.id = id;
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public MessageId getId() {
		return this.id;
	}

}
