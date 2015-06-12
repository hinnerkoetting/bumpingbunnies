package de.oetting.bumpingbunnies.core.networking.client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import de.oetting.bumpingbunnies.core.network.WlanDevice;
import de.oetting.bumpingbunnies.core.networking.init.DeviceDiscovery;
import de.oetting.bumpingbunnies.core.threads.BunniesThread;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class ListenForBroadcastsThread extends BunniesThread implements DeviceDiscovery {

	private static final Logger LOGGER = LoggerFactory.getLogger(ListenForBroadcastsThread.class);

	private final DatagramSocket socket;
	private final DatagramPacket packet;
	private final OnBroadcastReceived callback;

	private boolean canceled;

	public ListenForBroadcastsThread(DatagramSocket socket, OnBroadcastReceived callback,
			ThreadErrorCallback errorCallback) {
		super("Listening for broadcasts", errorCallback);
		this.socket = socket;
		this.callback = callback;
		byte[] buffer = new byte[1024];
		this.packet = new DatagramPacket(buffer, buffer.length);
	}

	@Override
	protected void doRun() throws Exception {
		while (!this.canceled) {
			try {
				oneRun();
			} catch (IOException e) {
				if (this.canceled) {
					LOGGER.info("exception because socket was closed. But Thread was already canceled.");
				} else {
					throw e;
				}
			}
		}
	}

	@Override
	public void searchServer() {
		start();
	}
	
	@Override
	public void closeConnections() {
		stopListening();
	}

	private void oneRun() throws IOException {
		this.socket.receive(this.packet);
		LOGGER.verbose("received broadcast message");
		if (this.packet.getData().length > 0) {
			InetAddress senderAddress = this.packet.getAddress();
			this.callback.broadcastReceived(new WlanDevice(senderAddress, new String(packet.getData())));
		}
	}

	public void stopListening() {
		this.canceled = true;
		closeSocket();
	}

	private void closeSocket() {
		LOGGER.info("Stop listening for broadcasts");
		this.socket.close();
	}
}
