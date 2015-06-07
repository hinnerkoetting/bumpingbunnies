package de.oetting.bumpingbunnies.core.game.steps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.TestConfigurationFactory;
import de.oetting.bumpingbunnies.core.game.OpponentTestFactory;
import de.oetting.bumpingbunnies.core.game.TestPlayerFactory;
import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.player.BunnyFactory;
import de.oetting.bumpingbunnies.core.game.spawnpoint.SpawnPointGenerator;
import de.oetting.bumpingbunnies.core.network.MessageSender;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.model.network.MessageId;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class HostBunnyKillCheckerTest {

	private HostBunnyKillChecker classUnderTest;
	private World world = new World();
	@Mock
	private SpawnPointGenerator spawnGenerator;
	@Mock
	private PlayerReviver reviver;
	@Mock
	private MessageSender sendControl;
	private Configuration configuration;
	@Mock
	private GameStopper gameStopper;

	@Test
	public void playerJoins_thenPlayerIsAlive() {
		Bunny player = new BunnyFactory(1).createPlayer(1, "", OpponentTestFactory.create());
		whenPlayerJoins(player);
		assertThat(player.isDead(), is(false));
	}

	@Test
	public void playerJoins_thenPlayerIsRevivedLater() {
		whenPlayerJoins(TestPlayerFactory.createOpponentPlayer());
		verify(this.reviver).revivePlayerLater(any(Bunny.class));
	}

	@Test
	public void playerJoins_thenPlayerCoordinateIsResetToSpawnPoint() {
		givenNextSpawnPointIsAt(1, 1);
		Bunny player = TestPlayerFactory.createOpponentPlayer();
		whenPlayerJoins(player);
		assertThat(player.getCenterX(), is(equalTo(1L)));
		assertThat(player.getCenterY(), is(equalTo(1L)));
	}

	@Test
	public void playerKilled_OnePlayerHas10PointsAnother0_endGameMessageIsSent() {
		Bunny player1 = TestPlayerFactory.createOpponentPlayer();
		player1.setScore(10);
		Bunny player2 = TestPlayerFactory.createOpponentPlayer();
		whenPlayerJoins(player1);
		whenPlayerJoins(player2);
		whenCheckingEndgameCondition();
		verify(gameStopper).gameStopped();
	}

	@Test
	public void playerKilled_OnePlayerHas9PointsAnother0_noEndGameMessageIsSent() {
		Bunny player1 = TestPlayerFactory.createOpponentPlayer();
		player1.setScore(9);
		Bunny player2 = TestPlayerFactory.createOpponentPlayer();
		whenPlayerJoins(player1);
		whenPlayerJoins(player2);
		whenCheckingEndgameCondition();
		verify(gameStopper, never()).gameStopped();
	}

	@Test
	public void playerKilled_OnePlayerHas10PointsAnother9_noEndGameMessageIsSent() {
		Bunny player1 = TestPlayerFactory.createOpponentPlayer();
		player1.setScore(10);
		Bunny player2 = TestPlayerFactory.createOpponentPlayer();
		player2.setScore(9);
		whenPlayerJoins(player1);
		whenPlayerJoins(player2);
		whenCheckingEndgameCondition();
		verify(gameStopper, never()).gameStopped();
	}
	
	@Test
	public void playerKilled_bothPlayersHave10Points_noEndGameMessageIsSent() {
		Bunny player1 = TestPlayerFactory.createOpponentPlayer();
		player1.setScore(10);
		Bunny player2 = TestPlayerFactory.createOpponentPlayer();
		player2.setScore(10);
		whenPlayerJoins(player1);
		whenPlayerJoins(player2);
		whenCheckingEndgameCondition();
		verify(sendControl, never()).sendMessage(eq(MessageId.STOP_GAME), any());
	}

	private void whenCheckingEndgameCondition() {
		classUnderTest.checkForEndgameCondition();
	}

	private void givenNextSpawnPointIsAt(long x, long y) {
		when(this.spawnGenerator.nextSpawnPoint()).thenReturn(new SpawnPoint(x, y));
	}

	private void whenPlayerJoins(Bunny player) {
		world.addBunny(player);
		this.classUnderTest.newEvent(player);
	}

	@Before
	public void setup() {
		initMocks(this);
		configuration = TestConfigurationFactory.createDummyHost();
		this.classUnderTest = new HostBunnyKillChecker(mock(CollisionDetection.class), this.world, this.spawnGenerator,
				this.reviver, this.sendControl, mock(PlayerDisconnectedCallback.class), mock(MusicPlayer.class),
				gameStopper, configuration, mock(ScoreboardSynchronisation.class));
		givenNextSpawnPointIsAt(0, 0);
	}
}
