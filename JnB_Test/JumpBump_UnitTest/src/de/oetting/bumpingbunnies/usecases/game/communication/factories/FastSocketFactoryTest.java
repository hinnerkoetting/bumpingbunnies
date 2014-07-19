package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import javax.xml.ws.Action;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.UdpSocket;
import de.oetting.bumpingbunnies.communication.UdpSocketFactory;
import de.oetting.bumpingbunnies.communication.wlan.TCPSocket;
import de.oetting.bumpingbunnies.tests.UnitTest;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

@Category(UnitTest.class)
public class FastSocketFactoryTest {

	private FastSocketFactory fixture;

	@Test
	public void create_tcpSocket_createsUdpSocket() throws IOException {
		MySocket socket = new TCPSocket(mock(Socket.class), mock(Opponent.class));
		MySocket fastSocket = whenCreatingSocket(socket);
		assertThat(fastSocket, is(instanceOf(UdpSocket.class)));
	}

	@Test(expected = FastSocketFactory.FastSocketNotPossible.class)
	public void create_othersocket_throwsException() {
		MySocket socket = new UdpSocket(mock(DatagramSocket.class), mock(InetAddress.class), 0, Opponent.createMyPlayer(""));
		whenCreatingSocket(socket);
	}

	private MySocket whenCreatingSocket(MySocket socket) {
		return this.fixture.create(socket, Opponent.createMyPlayer(""));
	}

	@Before
	public void beforeEveryTest() {
		this.fixture = new FastSocketFactory();
	}

	@Action
	public void afterEveryTest() {
		UdpSocketFactory.singleton().closeAndClearCreatedAdresses();
	}

}
