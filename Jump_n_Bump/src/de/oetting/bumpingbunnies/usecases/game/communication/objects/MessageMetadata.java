package de.oetting.bumpingbunnies.usecases.game.communication.objects;

public abstract class MessageMetadata<T> {

	private final MessageId id;
	private final Class<T> clazzOfMessage;

	public MessageMetadata(MessageId id, Class<T> clazzOfMessage) {
		super();
		this.id = id;
		this.clazzOfMessage = clazzOfMessage;
	}

	public Class<T> getClazz() {
		return this.clazzOfMessage;
	}

	public MessageId getId() {
		return this.id;
	}
}
