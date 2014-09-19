package de.oetting.bumpingbunnies.usecases.game.communication;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.tests.UnitTests;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.NetworkSendControl;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.RemoteConnectionFactory;

@Category(UnitTests.class)
public class NetworkToOtherClientsDispatcherTest {

	private NetworkToOtherClientsDispatcher fixture;
	private List<NetworkSender> sendQueues = new ArrayList<>();
	@Mock
	private MySocket incomingSocket;
	@Mock
	private NetworkToGameDispatcher gameDispatcher;

	private NetworkSendControl sendControl;

	@Test
	public void dispatchMessage_shouldDispatchMessageToGame() {
		JsonWrapper message = JsonWrapper.create(MessageId.PLAYER_IS_DEAD_MESSAGE, "message");
		this.fixture.dispatchMessage(message);
		verify(this.gameDispatcher).dispatchMessage(message);
	}

	@Test
	public void dispatchMessage_givenTheMessageCameFromTheOnlyExistingSocket_shouldNotDispatchMessageToThisSocket() {
		JsonWrapper message = JsonWrapper.create(MessageId.PLAYER_IS_DEAD_MESSAGE, "message");
		this.fixture.dispatchMessage(message);
		verify(this.sendQueues.get(0), never()).sendMessage(any(JsonWrapper.class));
	}

	@Test
	public void dispatchMesessage_givenThereIsAnotherSender_shouldDispatchMessageToThisSender() {
		NetworkSender networkSender = mock(NetworkSender.class);
		this.sendQueues.add(networkSender);
		this.fixture.dispatchMessage(JsonWrapper.create(MessageId.PLAYER_IS_DEAD_MESSAGE, "message"));
		verify(networkSender).sendMessage(any(JsonWrapper.class));
	}

	@Before
	public void beforeEveryTest() {
		MockitoAnnotations.initMocks(this);
		NetworkSender networkSender = createSenderForIncomingSocket();
		this.sendQueues.add(networkSender);
		this.sendControl = new NetworkSendControl(mock(RemoteConnectionFactory.class), this.sendQueues);
		this.fixture = new NetworkToOtherClientsDispatcher(this.incomingSocket, this.gameDispatcher, this.sendControl);
	}

	private NetworkSender createSenderForIncomingSocket() {
		NetworkSender networkSender = mock(NetworkSender.class);
		when(networkSender.usesThisSocket(this.incomingSocket)).thenReturn(true);
		return networkSender;
	}
}
