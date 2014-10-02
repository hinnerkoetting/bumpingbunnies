package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.oetting.bumpingbunnies.core.network.FastSocketFactory;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocket;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocketFactory;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.tests.IntegrationTests;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class FastSocketFactoryTest {

	private FastSocketFactory fixture;
	private MySocket socket;

	@Test
	public void create_tcpSocket_createsUdpSocket() throws IOException {
		socket = new TCPSocket(mock(Socket.class), mock(Opponent.class));
		MySocket fastSocket = whenCreatingSocket(socket);
		assertThat(fastSocket, is(instanceOf(UdpSocket.class)));
	}

	@Test(expected = FastSocketFactory.FastSocketNotPossible.class)
	public void create_othersocket_throwsException() {
		socket = new UdpSocket(mock(DatagramSocket.class), mock(InetAddress.class), 0, Opponent.createMyPlayer(""));
		whenCreatingSocket(socket);
	}

	private MySocket whenCreatingSocket(MySocket socket) {
		return this.fixture.create(socket, Opponent.createMyPlayer(""));
	}

	@Before
	public void beforeEveryTest() {
		this.fixture = new FastSocketFactory();
	}

	@After
	public void afterEveryTest() {
		UdpSocketFactory.singleton().closeAndClearCreatedAdresses();
		if (socket != null)
			socket.close();
	}

}
