package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import static de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory.createDummyPlayer;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.communication.RemoteConnection;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.WorldObjectsBuilder;

public class GameMainTest {

	private GameMain fixture;
	@Mock
	private PlayerJoinListener listener;

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
		this.fixture.playerJoins(createDummyPlayer());
	}

	@Test
	public void playerLeaves_shouldNotifyListener() {
		this.fixture.addJoinListener(this.listener);
		Player p = createDummyPlayer();
		givenPlayerExists(p);
		whenPlayerLeaves(p);
		verifyThatListenerIsNotifiedAboutLeaving(p);
	}

	@Test
	public void playerLeaves_thenPlayerShouldBeRemovedFromPlayerList() {
		Player p = createDummyPlayer();
		givenPlayerExists(p);
		assertNumberOfPlayers(1);
		whenPlayerLeaves(p);
		assertNumberOfPlayers(0);
	}

	@Test(expected = GameMain.ConnectionDoesNotExist.class)
	public void findConnection_givenOpponenDoesNotExist_shouldThrowException() {
		this.fixture.findConnection(OpponentFactory.createDummy());
	}

	@Test
	public void findConnection_givenConnectionDoesExist_shouldReturnConnection() {
		Opponent opponent = new Opponent("opponent");
		givenOpponentHasConnection(opponent);
		RemoteConnection connection = this.fixture.findConnection(opponent);
		assertNotNull(connection);
	}

	private void givenOpponentHasConnection(Opponent opponent) {
		RemoteConnection connection = new RemoteConnection(null, null, opponent);
		this.fixture.getSendThreads().add(connection);
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
		this.fixture.playerLeaves(p);
		this.fixture.getWorld().getAllPlayer().remove(p);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new GameMain();
		this.fixture.setWorld(new World(mock((WorldObjectsBuilder.class))));
	}
}
