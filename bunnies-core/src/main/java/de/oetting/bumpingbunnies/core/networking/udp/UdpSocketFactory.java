package de.oetting.bumpingbunnies.core.networking.udp;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.SocketException;

import de.oetting.bumpingbunnies.core.game.ConnectionIdentifierFactory;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.network.TcpSocketSettings;
import de.oetting.bumpingbunnies.model.network.UdpSocketSettings;

public class UdpSocketFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(UdpSocketFactory.class);

	public UdpSocket createListeningSocket(TCPSocket socket, ConnectionIdentifier owner) {
		TcpSocketSettings tcpSocketSettings = socket.getSocketSettings();
		UdpSocketSettings udpSocketSettings = new UdpSocketSettings(socket.getInetAddress(), tcpSocketSettings.getLocalPort(),
				tcpSocketSettings.getDestinationPort());
		return createListeningSocket(udpSocketSettings);
	}

	public UdpSocket createListeningSocket(UdpSocketSettings udpSocketSettings) {
		try {
			LOGGER.info("Creating listening UDP socket on local port %d ", udpSocketSettings.getLocalPort());
			UdpSocket udpSocket = createUdpSocket(udpSocketSettings, ConnectionIdentifierFactory.createListeningOpponent());
			udpSocket.connect();
			return udpSocket;
		} catch (IOException e) {
			LOGGER.info("Cannot bind to local port " + udpSocketSettings.getLocalPort());
			throw new UdpSocket.UdpException(e, udpSocketSettings.getLocalPort());
		}
	}

	public UdpSocket createSendingSocket(TCPSocket socket, ConnectionIdentifier owner) {
		TcpSocketSettings tcpSocketSettings = socket.getSocketSettings();
		UdpSocketSettings udpSocketSettings = new UdpSocketSettings(socket.getInetAddress(), tcpSocketSettings.getLocalPort(),
				tcpSocketSettings.getDestinationPort());
		try {
			LOGGER.info("Creating sending UDP socket on port %d and address %s ", udpSocketSettings.getDestinationPort(),
					udpSocketSettings.getDestinationAddress());
			UdpSocket udpSocket = createUdpSocket(udpSocketSettings, owner);
			return udpSocket;
		} catch (IOException e) {
			throw new UdpSocket.UdpException(e, udpSocketSettings.getLocalPort());
		}
	}

	public UdpSocket createUdpSocket(UdpSocketSettings settings, ConnectionIdentifier opponent) throws SocketException {
		DatagramSocket socket = new DatagramSocket(null);
		socket.setBroadcast(false);
		return new UdpSocket(socket, opponent, settings);
	}

	public UdpSocket createBroadcastSocket(UdpSocketSettings settings, ConnectionIdentifier opponent) {
		LOGGER.info("Creating Broadcast UDP socket on port %d and address %s ", settings.getDestinationPort(), settings.getDestinationAddress());
		try {
			DatagramSocket socket = new DatagramSocket(null);
			socket.setBroadcast(true);
			return new UdpSocket(socket, opponent, settings);
		} catch (IOException e) {
			throw new UdpSocket.UdpException(e, settings.getLocalPort());
		}
	}
}
