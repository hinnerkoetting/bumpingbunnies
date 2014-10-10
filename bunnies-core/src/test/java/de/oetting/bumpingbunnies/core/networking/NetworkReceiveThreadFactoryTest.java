package de.oetting.bumpingbunnies.core.networking;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.net.Socket;
import java.net.SocketAddress;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkReceiveThreadFactory;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.OpponentReceiverFactoryFactory;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;
import de.oetting.bumpingbunnies.model.network.TcpSocketSettings;
import de.oetting.bumpingbunnies.tests.IntegrationTests;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;

@Category(IntegrationTests.class)
public class NetworkReceiveThreadFactoryTest {

	private NetworkReceiveThreadFactory fixture;
	@Mock
	private SocketStorage sockets;
	@Mock
	private NetworkToGameDispatcher networkDispatcher;
	@Mock
	private NetworkMessageDistributor sendControl;

	@Test
	public void create_forMyPlayer_shouldNotReturnAnyThread() {
		List<NetworkReceiver> threads = whenCreatingReceiveThreads(OpponentType.LOCAL_PLAYER);
		assertThat(threads, hasSize(0));
	}

	@Test
	public void create_forNetworkPlayer_shouldReturnTwoThreads() {
		givenWlanSocket();
		List<NetworkReceiver> threads = whenCreatingReceiveThreads(OpponentType.WLAN);
		assertThat(threads, hasSize(2));
	}

	private void givenWlanSocket() {
		TcpSocketSettings settings = new TcpSocketSettings(mock(SocketAddress.class), 0, 1);
		when(this.sockets.findSocket(any(Opponent.class))).thenReturn(new TCPSocket(mock(Socket.class), mock(Opponent.class), settings));
	}

	@Test
	public void create_forAiPlayer_shouldNotReturnAnyThread() {
		List<NetworkReceiver> threads = whenCreatingReceiveThreads(OpponentType.AI);
		assertThat(threads, hasSize(0));
	}

	@Test
	public void create_forBluetoothPlayer_shouldReturnOneThread() {
		List<NetworkReceiver> threads = whenCreatingReceiveThreads(OpponentType.BLUETOOTH);
		assertThat(threads, hasSize(1));
	}

	private List<NetworkReceiver> whenCreatingReceiveThreads(OpponentType type) {
		return this.fixture.create(TestPlayerFactory.createOpponentPlayer(type));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new NetworkReceiveThreadFactory(this.sockets, this.networkDispatcher, this.sendControl, mock(OpponentReceiverFactoryFactory.class));
		when(this.sockets.findSocket(any(Opponent.class))).thenReturn(mock(MySocket.class));
	}

	@After
	public void afterEveryTest() {
		sockets.closeExistingSocket();
	}
}
