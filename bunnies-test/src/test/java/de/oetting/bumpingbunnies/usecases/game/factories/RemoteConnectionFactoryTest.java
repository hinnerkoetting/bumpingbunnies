package de.oetting.bumpingbunnies.usecases.game.factories;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.net.Socket;
import java.net.SocketAddress;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.core.network.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.core.network.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.TestSocket;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.DummyRemoteSender;
import de.oetting.bumpingbunnies.core.networking.messaging.UdpAndTcpNetworkSender;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;
import de.oetting.bumpingbunnies.model.network.TcpSocketSettings;
import de.oetting.bumpingbunnies.tests.IntegrationTests;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class RemoteConnectionFactoryTest {

	private RemoteConnectionFactory fixture;
	@Mock
	private SocketStorage sockets;
	@Mock
	private GameActivity activity;

	@Test
	public void create_forLocalPlayer_shouldReturnDummyConnection() {
		NetworkSender sender = this.fixture.create(TestPlayerFactory.createMyPlayer());
		assertThat(sender, is(instanceOf(DummyRemoteSender.class)));
	}

	@Test
	public void create_forWlanPlayer_shouldReturnDividedNetworkSender() {
		givenWlanSocket();
		NetworkSender sender = this.fixture.create(TestPlayerFactory.createOpponentPlayer(OpponentType.WLAN));
		assertThat(sender, is(instanceOf(UdpAndTcpNetworkSender.class)));
	}

	private void givenWlanSocket() {
		TcpSocketSettings settings = new TcpSocketSettings(mock(SocketAddress.class), 0, 1);
		when(this.sockets.findSocket(any(Opponent.class))).thenReturn(new TCPSocket(mock(Socket.class), mock(Opponent.class), settings));
	}

	@Test
	public void create_forBluetoothPlayer_shouldReturnNetworkSendQueueThread() {
		NetworkSender sender = this.fixture.create(TestPlayerFactory.createOpponentPlayer(OpponentType.BLUETOOTH));
		assertThat(sender, is(instanceOf(NetworkSendQueueThread.class)));
	}

	@Test
	public void create_foraiPlayer_shouldReturnDummyConnection() {
		NetworkSender sender = this.fixture.create(TestPlayerFactory.createOpponentPlayer(OpponentType.AI));
		assertThat(sender, is(instanceOf(DummyRemoteSender.class)));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new RemoteConnectionFactory(this.activity, this.sockets, mock(PlayerDisconnectedCallback.class));
		when(this.sockets.findSocket(any(Opponent.class))).thenReturn(new TestSocket());
	}

	@After
	public void afterEveryTest() {
		sockets.closeExistingSocket();
	}

}
