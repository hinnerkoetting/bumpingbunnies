package de.oetting.bumpingbunnies.core.networking.udp;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.oetting.bumpingbunnies.core.network.NetworkConstants;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;

public class UdpSocketFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(UdpSocketFactory.class);
	private final Map<InetAddress, UdpSocket> createdAdresses = new HashMap<InetAddress, UdpSocket>();

	private static UdpSocketFactory singleton;

	public static UdpSocketFactory singleton() {
		if (singleton == null) {
			singleton = new UdpSocketFactory();
		}
		return singleton;
	}

	private UdpSocketFactory() {
	}

	public UdpSocket create(TCPSocket socket, Opponent owner) {
		if (!singleton().createdAdresses.containsKey(socket.getInetAddress())) {
			createSocket(socket, owner);
		} else {
			LOGGER.warn("Reusing existing UDP-Port");
		}
		return singleton().createdAdresses.get(socket.getInetAddress());
	}

	private void createSocket(TCPSocket socket, Opponent owner) {
		try {
			int port = NetworkConstants.UDP_PORT;
			LOGGER.info("Creating UDP socket on port %d", port);
			DatagramSocket dataSocket = new DatagramSocket(port);
			dataSocket.setBroadcast(false);
			UdpSocket udpSocket = new UdpSocket(dataSocket, socket.getInetAddress(), port, owner);
			singleton().createdAdresses.put(socket.getInetAddress(), udpSocket);
		} catch (RuntimeException re) {
			throw re;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public void closeAndClearCreatedAdresses() {
		for (Entry<InetAddress, UdpSocket> entry : singleton().createdAdresses.entrySet()) {
			entry.getValue().close();
		}
		singleton().createdAdresses.clear();
	}
}
