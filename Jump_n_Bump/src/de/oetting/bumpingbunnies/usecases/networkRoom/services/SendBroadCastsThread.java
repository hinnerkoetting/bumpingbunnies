package de.oetting.bumpingbunnies.usecases.networkRoom.services;

import java.net.DatagramPacket;
import java.util.List;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.start.communication.UdpSocket;

public class SendBroadCastsThread extends Thread {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SendBroadCastsThread.class);
	private static final int BROASTCAST_SLEEP = 1000;
	private boolean canceled;
	private final List<UdpSocket> broadcastSockets;

	public SendBroadCastsThread(List<UdpSocket> broadcastSockets) {
		super("Sending broadcasts");
		this.broadcastSockets = broadcastSockets;
		LOGGER.info("Found %d broadcast addresses", broadcastSockets.size());
	}

	@Override
	public void run() {
		while (!this.canceled) {
			sendBroadcastMessage();
			sleep();
		}
	}

	private void sleep() {
		try {
			sleep(BROASTCAST_SLEEP);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private void sendBroadcastMessage() {
		LOGGER.debug("sending a new broadcast");
		for (UdpSocket socket : this.broadcastSockets) {
			String data = "hello";
			DatagramPacket packet = new DatagramPacket(data.getBytes(),
					data.length(), socket.getAddress(),
					NetworkConstants.BROADCAST_PORT);
			socket.send(packet);
		}
	}

	public void cancel() {
		this.canceled = true;
	}

	public void closeAllSockets() {
		for (UdpSocket socket : this.broadcastSockets) {
			try {
				socket.close();
			} catch (Exception e) {
				LOGGER.warn("Exception on closing broadcast socket "
						+ e.toString());
			}
		}

	}
}