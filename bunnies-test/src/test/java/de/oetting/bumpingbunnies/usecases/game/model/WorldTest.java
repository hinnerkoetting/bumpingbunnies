package de.oetting.bumpingbunnies.usecases.game.model;

import static de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory.createOpponentPlayer;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsSame;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.oetting.bumpingbunnies.core.world.PlayerDoesNotExist;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class WorldTest {

	private World fixture;

	@Test(expected = PlayerDoesNotExist.class)
	public void findPlayer_givenPlayerDoesNotExist_shouldThrowException() {
		this.fixture.findPlayer(0);
	}

	@Test
	public void findPlayer_givenPlayerDoesExist_returnsThisPlayer() {
		Player p = createOpponentPlayer();
		addPlayer(p);
		Player foundPlayer = this.fixture.findPlayer(p.id());
		assertThat(p, new IsSame<>(foundPlayer));
	}

	private void addPlayer(Player p) {
		this.fixture.getAllPlayer().add(p);
	}

	@Before
	public void beforeEveryTest() {
		this.fixture = new World();
	}
}
