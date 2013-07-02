package de.oetting.bumpingbunnies.usecases.game.communication.objects;

public class JsonWrapper {

	private final int id;
	private final String message;

	public JsonWrapper(int id, String message) {
		this.id = id;
		this.message = message;
	}

	public String getMessage() {
		return this.message;
	}

	public int getId() {
		return this.id;
	}

}
