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
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.core.networking.NetworkListener;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class ConnectionToClientServiceTest {

	private ConnectionToClientService fixture;
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
		verify(this.dispatcher).addObserver(eq(MessageId.SEND_CLIENT_LOCAL_PLAYER_SETTINGS), any(NetworkListener.class));
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
		this.fixture.onReceiveLocalPlayersettings(new LocalPlayerSettings("name"));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new ConnectionToClientService(this.roomActivity, this.networkReceiver, this.sockets);
		when(this.networkReceiver.getGameDispatcher()).thenReturn(this.dispatcher);
		when(this.roomActivity.getAllPlayersProperties()).thenReturn(Arrays.asList(new PlayerProperties(0, "my-player")));
	}
}
