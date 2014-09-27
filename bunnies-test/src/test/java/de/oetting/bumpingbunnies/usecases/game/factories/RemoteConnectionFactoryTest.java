package de.oetting.bumpingbunnies.usecases.game.factories;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.net.Socket;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.core.networking.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.TestSocket;
import de.oetting.bumpingbunnies.core.networking.messaging.DummyRemoteSender;
import de.oetting.bumpingbunnies.core.networking.messaging.UdpAndTcpNetworkSender;
import de.oetting.bumpingbunnies.core.networking.udp.UdpSocketFactory;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.TCPSocket;
import de.oetting.bumpingbunnies.tests.IntegrationTests;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.OpponentType;

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
		when(this.sockets.findSocket(any(Opponent.class))).thenReturn(new TCPSocket(mock(Socket.class), mock(Opponent.class)));
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
		this.fixture = new RemoteConnectionFactory(this.activity, this.sockets);
		when(this.sockets.findSocket(any(Opponent.class))).thenReturn(new TestSocket());
	}

	@After
	public void afterEveryTest() {
		UdpSocketFactory.singleton().closeAndClearCreatedAdresses();
	}

}
