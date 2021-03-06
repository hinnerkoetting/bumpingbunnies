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

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.network.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.server.NetworkToOtherClientsDispatcher;
import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;
import de.oetting.bumpingbunnies.model.network.MessageId;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class NetworkToOtherClientsDispatcherTest {

	private NetworkToOtherClientsDispatcher fixture;
	private List<NetworkSender> sendQueues = new ArrayList<>();
	@Mock
	private MySocket incomingSocket;
	@Mock
	private NetworkToGameDispatcher gameDispatcher;

	private NetworkMessageDistributor sendControl;

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
		this.sendControl = new NetworkMessageDistributor(mock(RemoteConnectionFactory.class), this.sendQueues);
		this.fixture = new NetworkToOtherClientsDispatcher(this.incomingSocket, this.gameDispatcher, this.sendControl, mock(World.class));
	}

	private NetworkSender createSenderForIncomingSocket() {
		NetworkSender networkSender = mock(NetworkSender.class);
		when(networkSender.usesThisSocket(this.incomingSocket)).thenReturn(true);
		return networkSender;
	}
}
