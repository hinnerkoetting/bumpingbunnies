package de.oetting.bumpingbunnies.usecases.networkRoom.services;

import java.net.InetAddress;

public interface OnBroadcastReceived {

	void broadcastReceived(InetAddress senderAddress);

	void errorOnBroadcastListening();
}
