package de.oetting.bumpingbunnies.core.network;

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
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.server.ToClientConnector;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.configuration.RemoteSettings;
import de.oetting.bumpingbunnies.model.network.MessageId;
import de.oetting.bumpingbunnies.tests.IntegrationTests;

@Category(IntegrationTests.class)
public class ToClientConnectorTest {

	private ToClientConnector classUnderTest;
	@Mock
	private AcceptsClientConnections roomActivity;
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
		verify(this.dispatcher).addObserver(eq(MessageId.CLIENT_REMOTE_SETTINGS), any(NetworkListener.class));
	}

	@Test
	public void onConnectToClient_thenNetworkReceiverShouldBeStarted() {
		onConnectToClient();
		verify(this.networkReceiver).start();
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
		verify(this.socketToNewPlayer).sendMessage(contains(MessageId.OTHER_PLAYER_PROPERTIES.toString()));
	}

	@Test
	public void clientSendsSettings_thenClientShouldReceiveHisClientId() {
		onConnectToClient();
		whenClientSendsSettings();
		verify(this.socketToNewPlayer).sendMessage(contains(MessageId.CLIENT_PLAYER_ID.toString()));
	}

	@Test
	public void clientSendsSettings_shouldInformAllOtherClientsAboutNewPlayer() {
		MySocket socket = mock(MySocket.class);
		givenThereExistsAnotherClient(socket);
		onConnectToClient();
		whenClientSendsSettings();
		verify(socket).sendMessage(contains(MessageId.OTHER_PLAYER_PROPERTIES.toString()));
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

	private void onConnectToClient() {
		this.classUnderTest.onConnectToClient(this.socketToNewPlayer);
	}

	private void givenThereExistsAnotherClient(MySocket socket) {
		when(this.sockets.getAllSockets()).thenReturn(Arrays.asList(socket));
	}

	private void whenClientSendsSettings() {
		this.classUnderTest.onReceiveRemotePlayersettings(new RemoteSettings("name"));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.classUnderTest = new ToClientConnector(this.roomActivity, this.networkReceiver, this.sockets, mock(PlayerDisconnectedCallback.class));
		when(this.networkReceiver.getGameDispatcher()).thenReturn(this.dispatcher);
		when(this.roomActivity.getAllPlayersProperties()).thenReturn(Arrays.asList(new PlayerProperties(0, "my-player")));
	}
}
