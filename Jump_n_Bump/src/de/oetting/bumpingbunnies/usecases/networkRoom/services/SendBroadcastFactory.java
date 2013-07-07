package de.oetting.bumpingbunnies.usecases.networkRoom.services;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.wifi.WifiManager;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.start.communication.UdpSocket;

public class SendBroadcastFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(SendBroadcastFactory.class);

	public static SendBroadCastsThread create(Context context) {
		try {
			List<UdpSocket> broadCastAddresses = getAllBroadCastSockets();
			return new SendBroadCastsThread(broadCastAddresses);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private static List<UdpSocket> getAllBroadCastSockets() throws Exception {
		List<UdpSocket> sockets = new ArrayList<UdpSocket>();
		for (Enumeration<NetworkInterface> en = NetworkInterface
				.getNetworkInterfaces(); en.hasMoreElements();) {
			NetworkInterface intf = en.nextElement();
			for (InterfaceAddress address : intf.getInterfaceAddresses()) {
				InetAddress broadcastAddress = address.getBroadcast();
				if (broadcastAddress != null) {
					try {
						sockets.add(openSocket(broadcastAddress));
					} catch (Exception e) {
						LOGGER.warn("Could not connect to one broadcast address "
								+ broadcastAddress.toString());
					}
				}
			}
		}
		return sockets;
	}

	/**
	 * Requires wifi access permission
	 * 
	 * @param context
	 * @return
	 * @throws IOException
	 */
	private static InetAddress findBroadcastAddress(Context context)
			throws IOException {
		WifiManager wifi = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		DhcpInfo dhcp = wifi.getDhcpInfo();
		// handle null somehow

		int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
		byte[] quads = new byte[4];
		for (int k = 0; k < 4; k++) {
			quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
		}
		return InetAddress.getByAddress(quads);
	}

	private static UdpSocket openSocket(InetAddress address) throws IOException {
		DatagramSocket socket = new DatagramSocket(
				NetworkConstants.BROADCAST_PORT);
		socket.setBroadcast(true);
		return new UdpSocket(socket, address);
	}
}