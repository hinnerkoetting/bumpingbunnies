package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import static de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory.createOpponentPlayer;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
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

import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.core.music.DummyMusicPlayer;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.network.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.DummyRemoteSender;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;
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
		verify(this.listener).newPlayerJoined(any(Player.class));
	}

	@Test
	public void playerJoins_thenThereShouldBeNewPlayerIsPlayerList() {
		assertNumberOfPlayers(0);
		whenPlayerJoins();
		assertNumberOfPlayers(1);
	}

	private void whenPlayerJoins() {
		this.fixture.newPlayerJoined(createOpponentPlayer());
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

	@Test
	public void playerJoins_givenRemotePlayer_shouldAddNewSendThread() {
		assertThat(this.sendThreads, hasSize(0));
		givenIsRemotePlayer();
		this.fixture.newPlayerJoined(TestPlayerFactory.createOpponentPlayer());
		assertThat(this.sendThreads, hasSize(1));
	}

	private void givenIsRemotePlayer() {
		when(this.sockets.existsSocket(any(Opponent.class))).thenReturn(true);
	}

	@Test
	public void playerJoins_forAiPlayer_shouldCreateNewDummyNetworkSender() {
		assertThat(this.sendThreads, hasSize(0));
		givenIsAiPlayer();
		this.fixture.newPlayerJoined(TestPlayerFactory.createOpponentPlayer(OpponentType.AI));
		assertThat(this.sendThreads, hasSize(1));
		assertThat(this.sendThreads.get(0), is(instanceOf(DummyRemoteSender.class)));
	}

	private void givenIsAiPlayer() {
		when(this.sockets.existsSocket(any(Opponent.class))).thenReturn(false);
	}

	private void assertNumberOfPlayers(int number) {
		assertThat(this.fixture.getWorld().getAllPlayer(), hasSize(number));
	}

	private void verifyThatListenerIsNotifiedAboutLeaving(Player p) {
		verify(this.listener).playerLeftTheGame(p);
	}

	private void givenPlayerExists(Player p) {
		this.fixture.getWorld().getAllPlayer().add(p);
	}

	private void whenPlayerLeaves(Player p) {
		this.fixture.playerLeftTheGame(p);
		this.fixture.getWorld().getAllPlayer().remove(p);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.sendThreads = new ArrayList<>();
		this.fixture = new GameMain(this.sockets, new DummyMusicPlayer());
		fixture.setSendControl(new NetworkMessageDistributor(mock(RemoteConnectionFactory.class)));
		fixture.setNewClientsAccepter(this.accepter);
		this.fixture.setWorld(new World());
		when(this.sockets.findSocket(any(Opponent.class))).thenReturn(mock(MySocket.class));
		NetworkMessageDistributor networkSendControl = createNetworkSendControl();
		this.fixture.addJoinListener(networkSendControl);
	}

	private NetworkMessageDistributor createNetworkSendControl() {
		NetworkMessageDistributor networkSendControl = new NetworkMessageDistributor(new RemoteConnectionFactory(mock(GameActivity.class),
				mock(SocketStorage.class), fixture), this.sendThreads);
		return networkSendControl;
	}
}
