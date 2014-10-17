package de.oetting.bumpingbunnies.core.networking.udp;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketAddress;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.oetting.bumpingbunnies.core.game.OpponentTestFactory;
import de.oetting.bumpingbunnies.core.network.FastSocketFactory;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.network.TcpSocketSettings;
import de.oetting.bumpingbunnies.model.network.UdpSocketSettings;
import de.oetting.bumpingbunnies.tests.IntegrationTests;

@Category(IntegrationTests.class)
public class FastSocketFactoryTest {

	private FastSocketFactory fixture;
	private MySocket socket;

	@Test
	public void create_tcpSocket_createsUdpSocket() throws IOException {
		TcpSocketSettings settings = new TcpSocketSettings(mock(SocketAddress.class), 0, 1);
		socket = new TCPSocket(mock(Socket.class), mock(Opponent.class), settings);
		MySocket fastSocket = whenCreatingSocket(socket);
		assertThat(fastSocket, is(instanceOf(UdpSocket.class)));
	}

	@Test(expected = FastSocketFactory.FastSocketNotPossible.class)
	public void create_othersocket_throwsException() {
		UdpSocketSettings settings = new UdpSocketSettings(mock(InetAddress.class), 0, 1);
		socket = new UdpSocket(mock(DatagramSocket.class), OpponentTestFactory.create(), settings);
		whenCreatingSocket(socket);
	}

	private MySocket whenCreatingSocket(MySocket socket) {
		return this.fixture.createSendingSocket(socket, OpponentTestFactory.create());
	}

	@Before
	public void beforeEveryTest() {
		this.fixture = new FastSocketFactory();
	}

	@After
	public void afterEveryTest() {
		if (socket != null)
			socket.close();
	}

}
