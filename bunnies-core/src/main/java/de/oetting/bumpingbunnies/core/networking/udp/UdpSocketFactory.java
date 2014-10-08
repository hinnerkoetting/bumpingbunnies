package de.oetting.bumpingbunnies.core.networking.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.network.TcpSocketSettings;
import de.oetting.bumpingbunnies.model.network.UdpSocketSettings;

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
		TcpSocketSettings tcpSocketSettings = socket.getSocketSettings();
		UdpSocketSettings udpSocketSettings = new UdpSocketSettings(socket.getInetAddress(), tcpSocketSettings.getLocalPort(),
				tcpSocketSettings.getDestinationPort());
		UdpSocket udpSocket = createUdpSocket(udpSocketSettings, owner);
		singleton().createdAdresses.put(socket.getInetAddress(), udpSocket);
	}

	public void closeAndClearCreatedAdresses() {
		for (Entry<InetAddress, UdpSocket> entry : singleton().createdAdresses.entrySet()) {
			entry.getValue().close();
		}
		singleton().createdAdresses.clear();
	}

	public UdpSocket createUdpSocket(UdpSocketSettings settings, Opponent opponent) {
		LOGGER.info("Creating normal UDP socket on port %d and address %s ", settings.getDestinationPort(), settings.getDestinationAddress());
		try {
			DatagramSocket socket = new DatagramSocket(null);
			socket.setBroadcast(false);
			SocketAddress localSocketAddress = new InetSocketAddress(settings.getLocalPort());
			socket.bind(localSocketAddress);
			return new UdpSocket(socket, opponent, settings);
		} catch (IOException e) {
			throw new UdpSocket.UdpException(e);
		}
	}

	public UdpSocket createBroadcastSocket(UdpSocketSettings settings, Opponent opponent) {
		LOGGER.info("Creating Broadcast UDP socket on port %d and address %s ", settings.getDestinationPort(), settings.getDestinationAddress());
		try {
			DatagramSocket socket = new DatagramSocket(null);
			socket.setBroadcast(true);
			return new UdpSocket(socket, opponent, settings);
		} catch (IOException e) {
			throw new UdpSocket.UdpException(e);
		}
	}
}
