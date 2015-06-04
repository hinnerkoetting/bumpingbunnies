package de.oetting.bumpingbunnies.usecases.game.model;

import static de.oetting.bumpingbunnies.core.game.TestPlayerFactory.createOpponentPlayer;
import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsSame;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.world.PlayerDoesNotExist;
import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class WorldTest {

	private World fixture;

	@Test(expected = PlayerDoesNotExist.class)
	public void findPlayer_givenPlayerDoesNotExist_shouldThrowException() {
		this.fixture.findBunny(0);
	}

	@Test
	public void findPlayer_givenPlayerDoesExist_returnsThisPlayer() {
		Bunny p = createOpponentPlayer();
		addPlayer(p);
		Bunny foundPlayer = this.fixture.findBunny(p.id());
		assertThat(p, new IsSame<>(foundPlayer));
	}

	private void addPlayer(Bunny p) {
		this.fixture.addBunny(p);
	}

	@Before
	public void beforeEveryTest() {
		this.fixture = new World();
	}
}
