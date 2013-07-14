package de.oetting.bumpingbunnies.usecases.networkRoom.services;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class ListenForBroadcastsThread extends Thread {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ListenForBroadcastsThread.class);
	private final DatagramSocket socket;
	private boolean canceled;
	private final DatagramPacket packet;
	private final OnBroadcastReceived callback;

	public ListenForBroadcastsThread(DatagramSocket socket,
			OnBroadcastReceived callback) {
		super("Listening for broadcasts");
		this.socket = socket;
		this.callback = callback;
		byte[] buffer = new byte[1024];
		this.packet = new DatagramPacket(buffer, buffer.length);
	}

	@Override
	public void run() {
		while (!this.canceled) {
			try {
				oneRun();
			} catch (Exception e) {
				if (this.canceled) {
					LOGGER.info("exception because socket was closed");
				} else {
					LOGGER.error("exception because socket was closed");
					displayErrorMessage();
				}
			}
		}
	}

	private void displayErrorMessage() {
		this.callback.errorOnBroadcastListening();
	}

	private void oneRun() throws IOException {
		this.socket.receive(this.packet);
		LOGGER.info("received broadcast message");
		if (this.packet.getData().length > 0) {
			InetAddress senderAddress = this.packet.getAddress();
			this.callback.broadcastReceived(senderAddress);
		}
	}

	public void cancel() {
		this.canceled = true;
	}

	public void closeSocket() {
		this.socket.close();
	}
}
