package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import de.oetting.bumpingbunnies.communication.UdpSocket;
import de.oetting.bumpingbunnies.communication.UdpSocketFactory;
import de.oetting.bumpingbunnies.communication.wlan.TCPSocket;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.tests.UnitTests;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

@Category(UnitTests.class)
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
