package de.oetting.bumpingbunnies.core.networking.server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import de.oetting.bumpingbunnies.core.network.NetworkConstants;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;

public class SendBroadcastFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(SendBroadcastFactory.class);

	public static SendBroadCastsThread create() {
		try {
			List<UdpSocket> broadCastAddresses = getAllBroadCastSockets();
			return new SendBroadCastsThread(broadCastAddresses);
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
		DatagramSocket socket = createUdpSocketPcWorkaround(address);
		socket.setBroadcast(true);
		LOGGER.info("Creating UDP socket on port %d and address %s ", NetworkConstants.BROADCAST_PORT, address.getHostAddress());
		return new UdpSocket(socket, address, NetworkConstants.BROADCAST_PORT, Opponent.createOpponent("UDP" + address.getHostAddress(), OpponentType.WLAN));
	}

	private static DatagramSocket createUdpSocketPcWorkaround(InetAddress address) throws SocketException {
		// This seems to be necessary so that we do not get a Address already in
		// use exception
		DatagramSocket socket = new DatagramSocket(null);
		socket.connect(address, NetworkConstants.BROADCAST_PORT);
		return socket;
	}
}
