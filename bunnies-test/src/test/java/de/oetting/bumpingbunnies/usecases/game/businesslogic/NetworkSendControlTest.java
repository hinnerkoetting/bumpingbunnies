package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.game.OpponentTestFactory;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.networking.TestSocket;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.UdpAndTcpNetworkSender;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class NetworkSendControlTest {

	private NetworkMessageDistributor fixture;
	private List<NetworkSender> sendThreads;
	@Mock
	private RemoteConnectionFactory factory;

	@Test
	public void playerJoins_shouldAddNewSendthread() {
		assertThat(this.sendThreads, hasSize(0));
		whenPlayerJoins();
		assertThat(this.sendThreads, hasSize(1));
	}

	@Test(expected = NetworkMessageDistributor.ConnectionDoesNotExist.class)
	public void findConnection_givenOpponenDoesNotExist_shouldThrowException() {
		this.fixture.findConnection(OpponentTestFactory.create());
	}

	@Test
	public void findConnection_givenConnectionDoesExist_shouldReturnConnection() {
		ConnectionIdentifier opponent = OpponentTestFactory.create();
		givenOpponentHasConnection(opponent);
		NetworkSender connection = this.fixture.findConnection(opponent);
		assertNotNull(connection);
	}

	private void givenOpponentHasConnection(ConnectionIdentifier opponent) {
		UdpAndTcpNetworkSender connection = new UdpAndTcpNetworkSender(null, null, opponent);
		this.sendThreads.add(connection);
	}

	private void whenPlayerJoins() {
		this.fixture.newEvent(new TestSocket());
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.sendThreads = new LinkedList<>();
		this.fixture = new NetworkMessageDistributor(this.factory, this.sendThreads);
		when(this.factory.create(any(MySocket.class))).thenReturn(mock(NetworkSender.class));
	}
}
