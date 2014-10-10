package de.oetting.bumpingbunnies.communication;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.oetting.bumpingbunnies.core.networking.udp.UdpSocket;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocketFactory;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.model.game.objects.OpponentTestFactory;
import de.oetting.bumpingbunnies.model.network.TcpSocketSettings;
import de.oetting.bumpingbunnies.tests.IntegrationTests;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class UdpSocketFactoryTest {

	private UdpSocket socket1;
	private UdpSocket socket2;

	@Test
	public void create_twoTimes_returnsSocketFromFirsttime() throws UnknownHostException, IOException {
		Socket socket = mock(Socket.class);
		when(socket.getInetAddress()).thenReturn(mock(InetAddress.class));
		TcpSocketSettings settings = new TcpSocketSettings(mock(SocketAddress.class), 0, 1);
		TCPSocket wlanSocket = new TCPSocket(socket, OpponentTestFactory.create(), settings);
		socket1 = UdpSocketFactory.singleton().create(wlanSocket, OpponentTestFactory.create());
		socket2 = UdpSocketFactory.singleton().create(wlanSocket, OpponentTestFactory.create());
		assertSame(socket1, socket2);
	}

	@After
	public void tearDown() {
		if (socket1 != null)
			socket1.close();
		if (socket2 != null)
			socket2.close();
	}
}
