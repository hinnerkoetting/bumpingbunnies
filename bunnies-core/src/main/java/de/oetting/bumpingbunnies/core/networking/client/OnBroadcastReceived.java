package de.oetting.bumpingbunnies.core.networking.client;

import java.net.InetAddress;

public interface OnBroadcastReceived {

	void broadcastReceived(InetAddress senderAddress);

	void errorOnBroadcastListening();
}
