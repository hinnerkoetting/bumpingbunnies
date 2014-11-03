package de.oetting.bumpingbunnies.core.game;

import static de.oetting.bumpingbunnies.core.game.TestPlayerFactory.createOpponentPlayer;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.core.music.DummyMusicPlayer;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NetworkPlayerStateSenderThread;
import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.network.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class GameMainTest {

	private GameMain fixture;
	@Mock
	private PlayerJoinListener listener;
	private List<NetworkSender> sendThreads;
	@Mock
	private SocketStorage sockets;
	@Mock
	private NewClientsAccepter accepter;

	@Test
	public void playerJoins_shouldNotifyListener() {
		this.fixture.addJoinListener(this.listener);
		whenPlayerJoins();
		verifyThatListenerIsNotifiedAboutJoin();
	}

	private void verifyThatListenerIsNotifiedAboutJoin() {
		verify(this.listener).newEvent(any(Player.class));
	}

	@Test
	public void playerJoins_thenThereShouldBeNewPlayerIsPlayerList() {
		assertNumberOfPlayers(0);
		whenPlayerJoins();
		assertNumberOfPlayers(1);
	}

	private void whenPlayerJoins() {
		this.fixture.newEvent(createOpponentPlayer());
	}

	@Test
	public void playerLeaves_shouldNotifyListener() {
		this.fixture.addJoinListener(this.listener);
		Player p = createOpponentPlayer();
		givenPlayerExists(p);
		whenPlayerLeaves(p);
		verifyThatListenerIsNotifiedAboutLeaving(p);
	}

	@Test
	public void playerLeaves_thenPlayerShouldBeRemovedFromPlayerList() {
		Player p = createOpponentPlayer();
		givenPlayerExists(p);
		assertNumberOfPlayers(1);
		whenPlayerLeaves(p);
		assertNumberOfPlayers(0);
	}

	private void assertNumberOfPlayers(int number) {
		assertThat(this.fixture.getWorld().getAllPlayer(), hasSize(number));
	}

	private void verifyThatListenerIsNotifiedAboutLeaving(Player p) {
		verify(this.listener).removeEvent(p);
	}

	private void givenPlayerExists(Player p) {
		fixture.newEvent(p);
	}

	private void whenPlayerLeaves(Player p) {
		this.fixture.removeEvent(p);
		this.fixture.getWorld().getAllPlayer().remove(p);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.sendThreads = new ArrayList<NetworkSender>();
		this.fixture = new GameMain(this.sockets, new DummyMusicPlayer(), mock(NetworkPlayerStateSenderThread.class), mock(NetworkMessageDistributor.class));
		fixture.setNewClientsAccepter(this.accepter);
		this.fixture.setWorld(new World());
		when(this.sockets.findSocket(any(ConnectionIdentifier.class))).thenReturn(mock(MySocket.class));
		NetworkMessageDistributor networkSendControl = createNetworkSendControl();
		SocketStorage.getSingleton().addObserver(networkSendControl);
	}

	private NetworkMessageDistributor createNetworkSendControl() {
		NetworkMessageDistributor networkSendControl = new NetworkMessageDistributor(new RemoteConnectionFactory(mock(ThreadErrorCallback.class)),
				this.sendThreads);
		return networkSendControl;
	}
}
