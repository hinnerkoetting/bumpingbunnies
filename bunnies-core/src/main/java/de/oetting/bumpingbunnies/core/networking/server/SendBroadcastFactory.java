package de.oetting.bumpingbunnies.core.networking.server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.ConnectionIdentifierFactory;
import de.oetting.bumpingbunnies.core.network.NetworkConstants;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocket;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocketFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.network.UdpSocketSettings;

public class SendBroadcastFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(SendBroadcastFactory.class);

	public static SendBroadCastsThread create(ThreadErrorCallback errorCallback, String hostname) {
		try {
			List<UdpSocket> broadCastAddresses = getAllBroadCastSockets();
			return new SendBroadCastsThread(broadCastAddresses, errorCallback, hostname);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static List<UdpSocket> getAllBroadCastSockets() throws Exception {
		List<UdpSocket> sockets = new ArrayList<UdpSocket>();
		for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
			NetworkInterface intf = en.nextElement();
			for (InterfaceAddress address : intf.getInterfaceAddresses()) {
				InetAddress broadcastAddress = address.getBroadcast();
				if (broadcastAddress != null) {
					try {
						sockets.add(openSocket(broadcastAddress));
					} catch (Exception e) {
						LOGGER.warn("Could not connect to one broadcast address " + broadcastAddress.toString() + " error is " + e.getMessage());
					}
				}
			}
		}
		return sockets;
	}

	private static UdpSocket openSocket(InetAddress address) throws IOException {
		UdpSocketSettings settings = new UdpSocketSettings(address, NetworkConstants.BROADCAST_PORT, NetworkConstants.BROADCAST_PORT);
		return new UdpSocketFactory().createBroadcastSocket(settings, ConnectionIdentifierFactory.createBroadcastOpponent());
	}

}
