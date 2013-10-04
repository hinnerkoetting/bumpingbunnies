package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;

public class PlayerReviverTest {

	private PlayerReviver fixture;
	private List<PlayerReviveEntry> reviveEntries = new LinkedList<>();

	@Test
	public void playerJoins_shouldAddReviveMessage() {
		assertThat(this.reviveEntries, hasSize(0));
		this.fixture.newPlayerJoined(TestPlayerFactory.createDummyPlayer());
		assertThat(this.reviveEntries, hasSize(1));
	}

	@Before
	public void beforeEveryTest() {
		this.fixture = new PlayerReviver(new ArrayList<RemoteSender>(), this.reviveEntries);
	}
}
