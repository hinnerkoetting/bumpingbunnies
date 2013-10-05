package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.spawnpoint.SpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.communication.ThreadedNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class HostBunnyKillCheckerTest {

	private HostBunnyKillChecker fixture;
	@Mock
	private World world;
	@Mock
	private SpawnPointGenerator spawnGenerator;
	@Mock
	private PlayerReviver reviver;

	@Test
	public void playerJoins_thenPlayerIsDead() {
		Player player = TestPlayerFactory.createOpponentPlayer();
		whenPlayerJoins(player);
		assertThat(player.isDead(), is(true));
	}

	@Test
	public void playerJoins_thenPlayerIsRevivedLater() {
		whenPlayerJoins(TestPlayerFactory.createOpponentPlayer());
		verify(this.reviver).revivePlayerLater(any(Player.class));
	}

	@Test
	public void playerJoins_thenPlayerCoordinateIsResetToSpawnPoint() {
		givenNextSpawnPointIsAt(1, 1);
		Player player = TestPlayerFactory.createOpponentPlayer();
		whenPlayerJoins(player);
		assertThat(player.getCenterX(), is(equalTo(1L)));
		assertThat(player.getCenterY(), is(equalTo(1L)));
	}

	private void givenNextSpawnPointIsAt(long x, long y) {
		when(this.spawnGenerator.nextSpawnPoint()).thenReturn(new SpawnPoint(x, y));
	}

	private void whenPlayerJoins(Player player) {
		this.fixture.newPlayerJoined(player);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new HostBunnyKillChecker(new ArrayList<ThreadedNetworkSender>(), mock(CollisionDetection.class), this.world,
				this.spawnGenerator, this.reviver);
		givenNextSpawnPointIsAt(0, 0);
	}
}