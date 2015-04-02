package de.oetting.bumpingbunnies.core.networking.server;

import java.util.List;

import de.oetting.bumpingbunnies.core.networking.udp.UdpSocket;
import de.oetting.bumpingbunnies.core.threads.BunniesThread;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class SendBroadCastsThread extends BunniesThread {

	private static final Logger LOGGER = LoggerFactory.getLogger(SendBroadCastsThread.class);
	private static final int BROASTCAST_SLEEP = 1000;
	private final List<UdpSocket> broadcastSockets;
	private final String hostName;
	private boolean canceled;

	public SendBroadCastsThread(List<UdpSocket> broadcastSockets, ThreadErrorCallback errorCallback, String hostName) {
		super("Sending broadcasts", errorCallback);
		this.broadcastSockets = broadcastSockets;
		this.hostName = hostName;
		LOGGER.info("Found %d broadcast addresses", broadcastSockets.size());
	}

	@Override
	protected void doRun() throws Exception {
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
		for (UdpSocket socket : this.broadcastSockets) {
			socket.sendMessage(hostName);
		}
	}

	public void cancel() {
		this.canceled = true;
	}

	public void closeAllSockets() {
		for (UdpSocket socket : this.broadcastSockets) {
			try {
				LOGGER.info("Closing Send-Broadcast socket");
				socket.close();
			} catch (Exception e) {
				LOGGER.warn("Exception on closing broadcast socket " + e.toString());
			}
		}

	}
}
