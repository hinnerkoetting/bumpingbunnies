package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;

public interface MySocket {

	void close();

	void connect();

	void sendMessage(String message);

	String blockingReceive();

	boolean isFastSocketPossible();

	ConnectionIdentifier getOwner();

	String getRemoteDescription();

	String getLocalDescription();

}
