package de.oetting.bumpingbunnies.core.networking.server;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import de.oetting.bumpingbunnies.core.networking.NetworkConstants;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.OpponentType;

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

	// /**
	// * Requires wifi access permission
	// *
	// * @param context
	// * @return
	// * @throws IOException
	// */
	// private static InetAddress findBroadcastAddress(Context context)
	// throws IOException {
	// WifiManager wifi = (WifiManager) context
	// .getSystemService(Context.WIFI_SERVICE);
	// DhcpInfo dhcp = wifi.getDhcpInfo();
	// // handle null somehow
	//
	// int broadcast = (dhcp.ipAddress & dhcp.netmask) | ~dhcp.netmask;
	// byte[] quads = new byte[4];
	// for (int k = 0; k < 4; k++) {
	// quads[k] = (byte) ((broadcast >> k * 8) & 0xFF);
	// }
	// return InetAddress.getByAddress(quads);
	// }

	private static UdpSocket openSocket(InetAddress address) throws IOException {
		DatagramSocket socket = new DatagramSocket(NetworkConstants.BROADCAST_PORT);
		socket.setBroadcast(true);
		LOGGER.info("Creating UDP socket on port %d", NetworkConstants.BROADCAST_PORT);
		return new UdpSocket(socket, address, NetworkConstants.BROADCAST_PORT, Opponent.createOpponent("UDP" + address.getHostAddress(), OpponentType.WLAN));
	}
}
