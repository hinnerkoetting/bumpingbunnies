package de.oetting.bumpingbunnies.usecases.game.model;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsSame;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import android.content.Context;
import de.oetting.bumpingbunnies.usecases.game.model.worldfactory.WorldObjectsBuilder;

public class WorldTest {

	private World fixture;

	@Mock
	private WorldObjectsBuilder worldBuilder;
	@Mock
	private Context context;

	@Test(expected = World.PlayerDoesNotExist.class)
	public void findPlayer_givenPlayerDoesNotExist_shouldThrowException() {
		this.fixture.findPlayer(0);
	}

	@Test
	public void findPlayer_givenPlayerDoesExist_returnsThisPlayer() {
		Player p = new Player(0, "exists", 1);
		addPlayer(p);
		Player foundPlayer = this.fixture.findPlayer(0);
		assertThat(p, new IsSame<>(foundPlayer));
	}

	private void addPlayer(Player p) {
		this.fixture.getAllPlayer().add(p);
	}

	@Before
	public void beforeEveryTest() {
		this.fixture = new World(this.worldBuilder);
	}
}
