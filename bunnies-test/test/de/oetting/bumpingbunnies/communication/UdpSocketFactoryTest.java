package de.oetting.bumpingbunnies.communication;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.oetting.bumpingbunnies.communication.wlan.TCPSocket;
import de.oetting.bumpingbunnies.tests.IntegrationTests;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

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
		TCPSocket wlanSocket = new TCPSocket(socket, Opponent.createMyPlayer(""));
		socket1 = UdpSocketFactory.singleton().create(wlanSocket, Opponent.createMyPlayer(""));
		socket2 = UdpSocketFactory.singleton().create(wlanSocket, Opponent.createMyPlayer(""));
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
