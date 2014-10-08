package de.oetting.bumpingbunnies.usecases.networkRoom.services;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.contains;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkListener;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.server.ToClientConnector;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.configuration.RemoteSettings;
import de.oetting.bumpingbunnies.model.network.MessageId;
import de.oetting.bumpingbunnies.tests.IntegrationTests;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class ConnectionToClientServiceTest {

	private ToClientConnector fixture;
	@Mock
	private RoomActivity roomActivity;
	@Mock
	private NetworkReceiver networkReceiver;
	@Mock
	private NetworkToGameDispatcher dispatcher;
	@Mock
	private MySocket socketToNewPlayer;
	@Mock
	private SocketStorage sockets;

	@Test
	public void onConnectToClient_thenLocalSettingsReceiverShouldBeAddedToNetworkReceiverObservers() {
		onConnectToClient();
		verify(this.dispatcher).addObserver(eq(MessageId.SEND_CLIENT_REMOTE_SETTINGS), any(NetworkListener.class));
	}

	@Test
	public void onConnectToClient_thenNetworkReceiverShouldBeStarted() {
		onConnectToClient();
		verify(this.networkReceiver).start();
	}

	private void onConnectToClient() {
		this.fixture.onConnectToClient(this.socketToNewPlayer);
	}

	@Test
	public void clientSendsSettings_thenReceiverShouldBeCanceled() {
		onConnectToClient();
		whenClientSendsSettings();
		verify(this.networkReceiver).cancel();
	}

	@Test
	public void clientSendsSettings_thenClientShouldBeInformedAboutMyPlayer() {
		onConnectToClient();
		whenClientSendsSettings();
		verify(this.socketToNewPlayer).sendMessage(contains(MessageId.SEND_OTHER_PLAYER_ID.toString()));
	}

	@Test
	public void clientSendsSettings_thenClientShouldReceiveHisClientId() {
		onConnectToClient();
		whenClientSendsSettings();
		verify(this.socketToNewPlayer).sendMessage(contains(MessageId.SEND_CLIENT_PLAYER_ID.toString()));
	}

	@Test
	public void clientSendsSettings_shouldInformAllOtherClientsAboutNewPlayer() {
		MySocket socket = mock(MySocket.class);
		givenThereExistsAnotherClient(socket);
		onConnectToClient();
		whenClientSendsSettings();
		verify(socket).sendMessage(contains(MessageId.SEND_OTHER_PLAYER_ID.toString()));
	}

	@Test
	public void clientSendsSettings_shouldAddNewSocket() {
		onConnectToClient();
		whenClientSendsSettings();
		verify(this.sockets).addSocket(any(MySocket.class));
	}

	@Test
	public void clientSendsSettings_shouldAddNewPlayer() {
		onConnectToClient();
		whenClientSendsSettings();
		verify(this.roomActivity).addPlayerEntry(any(MySocket.class), any(PlayerProperties.class), anyInt());
	}

	private void givenThereExistsAnotherClient(MySocket socket) {
		when(this.roomActivity.getAllOtherSockets()).thenReturn(Arrays.asList(socket));
	}

	private void whenClientSendsSettings() {
		this.fixture.onReceiveRemotePlayersettings(new RemoteSettings(0, "name"));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new ToClientConnector(this.roomActivity, this.networkReceiver, this.sockets);
		when(this.networkReceiver.getGameDispatcher()).thenReturn(this.dispatcher);
		when(this.roomActivity.getAllPlayersProperties()).thenReturn(Arrays.asList(new PlayerProperties(0, "my-player")));
	}
}
