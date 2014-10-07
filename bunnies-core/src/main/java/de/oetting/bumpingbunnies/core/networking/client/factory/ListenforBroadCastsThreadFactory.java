package de.oetting.bumpingbunnies.core.networking.client.factory;

import java.net.DatagramSocket;
import java.net.SocketException;

import de.oetting.bumpingbunnies.core.network.NetworkConstants;
import de.oetting.bumpingbunnies.core.networking.client.CouldNotOpenBroadcastSocketException;
import de.oetting.bumpingbunnies.core.networking.client.ListenForBroadcastsThread;
import de.oetting.bumpingbunnies.core.networking.client.OnBroadcastReceived;

public class ListenforBroadCastsThreadFactory {

	public static ListenForBroadcastsThread create(OnBroadcastReceived callback) {
		try {
			DatagramSocket udpSocket = new DatagramSocket(NetworkConstants.BROADCAST_PORT);
			return new ListenForBroadcastsThread(udpSocket, callback);
		} catch (SocketException e) {
			throw new CouldNotOpenBroadcastSocketException(e);
		}
	}
}