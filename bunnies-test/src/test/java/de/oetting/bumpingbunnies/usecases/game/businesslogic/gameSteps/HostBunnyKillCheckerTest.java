package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.spawnpoint.SpawnPointGenerator;
import de.oetting.bumpingbunnies.core.game.steps.HostBunnyKillChecker;
import de.oetting.bumpingbunnies.core.game.steps.PlayerReviver;
import de.oetting.bumpingbunnies.core.network.MessageSender;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.tests.UnitTests;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;

@Category(UnitTests.class)
public class HostBunnyKillCheckerTest {

	private HostBunnyKillChecker fixture;
	@Mock
	private World world;
	@Mock
	private SpawnPointGenerator spawnGenerator;
	@Mock
	private PlayerReviver reviver;
	@Mock
	private MessageSender sendControl;

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
		this.fixture = new HostBunnyKillChecker(mock(CollisionDetection.class), this.world, this.spawnGenerator, this.reviver, this.sendControl);
		givenNextSpawnPointIsAt(0, 0);
	}
}
