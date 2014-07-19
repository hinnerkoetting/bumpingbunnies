package de.oetting.bumpingbunnies.communication;

import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import de.oetting.bumpingbunnies.communication.wlan.TCPSocket;
import de.oetting.bumpingbunnies.tests.IntegrationTest;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

@Category(IntegrationTest.class)
@RunWith(RobolectricTestRunner.class)
public class UdpSocketFactoryTest {

	@Test
	public void create_twoTimes_returnsSocketFromFirsttime() throws UnknownHostException, IOException {
		Socket socket = mock(Socket.class);
		when(socket.getInetAddress()).thenReturn(mock(InetAddress.class));
		TCPSocket wlanSocket = new TCPSocket(socket, Opponent.createMyPlayer(""));
		UdpSocket create1 = UdpSocketFactory.singleton().create(wlanSocket, Opponent.createMyPlayer(""));
		UdpSocket create2 = UdpSocketFactory.singleton().create(wlanSocket, Opponent.createMyPlayer(""));
		assertSame(create1, create2);
	}
}
