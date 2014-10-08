package de.oetting.bumpingbunnies.core.networking;

import java.io.IOException;
import java.net.DatagramSocket;

import de.oetting.bumpingbunnies.core.network.NetworkConstants;

public class FreePortFinder {

	public int findFreePort() {
		int maxTries = 10;
		int count = 0;
		while (count++ < maxTries) {
			int nextport = NetworkConstants.UDP_PORT + count - 1;
			if (portIsOpen(nextport)) {
				return nextport;
			}
		}
		throw new CouldNotFindPortException();
	}

	private boolean portIsOpen(int nextport) {
		DatagramSocket udpSocket = null;
		java.net.ServerSocket tcpSocket = null;
		try {
			udpSocket = new DatagramSocket(nextport);
			udpSocket.setReuseAddress(true);
			tcpSocket = new java.net.ServerSocket(nextport);
			tcpSocket.setReuseAddress(true);
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			if (udpSocket != null)
				udpSocket.close();
			if (tcpSocket != null)
				try {
					tcpSocket.close();
				} catch (IOException e) {
					// ignore
				}

		}

	}

	public static class CouldNotFindPortException extends RuntimeException {
		public CouldNotFindPortException() {
			super("No free port could be found while trying 10 different ports");
		}
	}

}
