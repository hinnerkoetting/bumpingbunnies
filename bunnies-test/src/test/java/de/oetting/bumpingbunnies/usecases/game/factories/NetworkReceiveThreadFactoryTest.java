package de.oetting.bumpingbunnies.usecases.game.factories;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.net.Socket;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.android.game.SocketStorage;
import de.oetting.bumpingbunnies.communication.NetworkSendControl;
import de.oetting.bumpingbunnies.communication.UdpSocketFactory;
import de.oetting.bumpingbunnies.communication.wlan.TCPSocket;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.tests.UnitTests;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiveThread;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.NetworkReceiveThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.OpponentType;

@Category(UnitTests.class)
public class NetworkReceiveThreadFactoryTest {

	private NetworkReceiveThreadFactory fixture;
	@Mock
	private SocketStorage sockets;
	@Mock
	private NetworkToGameDispatcher networkDispatcher;
	@Mock
	private NetworkSendControl sendControl;

	@Test
	public void create_forMyPlayer_shouldNotReturnAnyThread() {
		List<NetworkReceiveThread> threads = whenCreatingReceiveThreads(OpponentType.MY_PLAYER);
		assertThat(threads, hasSize(0));
	}

	@Test
	public void create_forNetworkPlayer_shouldReturnTwoThreads() {
		givenWlanSocket();
		List<NetworkReceiveThread> threads = whenCreatingReceiveThreads(OpponentType.WLAN);
		assertThat(threads, hasSize(2));
	}

	private void givenWlanSocket() {
		when(this.sockets.findSocket(any(Opponent.class))).thenReturn(new TCPSocket(mock(Socket.class), mock(Opponent.class)));
	}

	@Test
	public void create_forAiPlayer_shouldNotReturnAnyThread() {
		List<NetworkReceiveThread> threads = whenCreatingReceiveThreads(OpponentType.AI);
		assertThat(threads, hasSize(0));
	}

	@Test
	public void create_forBluetoothPlayer_shouldReturnOneThread() {
		List<NetworkReceiveThread> threads = whenCreatingReceiveThreads(OpponentType.BLUETOOTH);
		assertThat(threads, hasSize(1));
	}

	private List<NetworkReceiveThread> whenCreatingReceiveThreads(OpponentType type) {
		return this.fixture.create(TestPlayerFactory.createOpponentPlayer(type));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new NetworkReceiveThreadFactory(this.sockets, this.networkDispatcher, this.sendControl);
		when(this.sockets.findSocket(any(Opponent.class))).thenReturn(mock(MySocket.class));
	}

	@After
	public void afterEveryTest() {
		UdpSocketFactory.singleton().closeAndClearCreatedAdresses();
	}
}
