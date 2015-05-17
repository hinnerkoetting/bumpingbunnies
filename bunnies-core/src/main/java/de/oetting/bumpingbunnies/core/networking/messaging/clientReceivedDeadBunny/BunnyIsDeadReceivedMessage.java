package de.oetting.bumpingbunnies.core.networking.messaging.clientReceivedDeadBunny;

public class BunnyIsDeadReceivedMessage {

	private final int bunnyId;

	public BunnyIsDeadReceivedMessage(int bunnyId) {
		this.bunnyId = bunnyId;
	}

	public int getBunnyId() {
		return bunnyId;
	}

}
