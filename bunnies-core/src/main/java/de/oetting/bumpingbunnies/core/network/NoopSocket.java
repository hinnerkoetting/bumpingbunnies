package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;

public class NoopSocket implements MySocket {

	private final ConnectionIdentifier identifier;

	public NoopSocket(ConnectionIdentifier identifier) {
		this.identifier = identifier;
	}

	@Override
	public void close() {
	}

	@Override
	public void connect() {
	}

	@Override
	public void sendMessage(String message) {
	}

	@Override
	public String blockingReceive() {
		throw new IllegalArgumentException();
	}

	@Override
	public boolean isFastSocketPossible() {
		return false;
	}

	@Override
	public ConnectionIdentifier getConnectionIdentifier() {
		return identifier;
	}

	@Override
	public String getRemoteDescription() {
		throw new IllegalArgumentException();
	}

	@Override
	public String getLocalDescription() {
		throw new IllegalArgumentException();
	}

}
