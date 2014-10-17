package de.oetting.bumpingbunnies.core.game;

import static de.oetting.bumpingbunnies.core.game.CameraPositionCalculation.SCROLLING_WHILE_PLAYER_IS_DEAD;
import static de.oetting.bumpingbunnies.core.game.TestPlayerFactory.createOpponentPlayer;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
@RunWith(MockitoJUnitRunner.class)
public class CameraPositionCalculationTest {

	private CameraPositionCalculation fixture;
	private Player player;

	@Test
	public void updateCoordinats_givenPlayerIsNotDead_setsScreenCoordinatesToPlayerCoordinates() {
		this.player.setCenterX(100);
		this.player.setCenterY(100);
		this.fixture.updateScreenPosition();
		assertEquals(100, this.fixture.getCurrentScreenX());
		assertEquals(100, this.fixture.getCurrentScreenY());
	}

	@Test
	public void smoothUpdateCoordinats_givenScreenInOnPlayer_doesNotChangeScreen() {
		smoothUpdate();
		assertEquals(0, this.fixture.getCurrentScreenX());
		assertEquals(0, this.fixture.getCurrentScreenY());
	}

	@Test
	public void smoothUpdateCoordinats_givenScreenIsALittleDifferent_updatesScreenToPlayerPosition() {
		this.player.setCenterX(SCROLLING_WHILE_PLAYER_IS_DEAD / 2);
		this.player.setCenterY(SCROLLING_WHILE_PLAYER_IS_DEAD / 2);
		smoothUpdate();
		assertEquals(this.player.getCenterX(), this.fixture.getCurrentScreenX());
		assertEquals(this.player.getCenterY(), this.fixture.getCurrentScreenY());
	}

	@Test
	public void smoothUpdate_givenScreenIsExactlyByOffsetAway_updatesScreenToPlayerPosition() {
		this.player.setCenterX(SCROLLING_WHILE_PLAYER_IS_DEAD);
		this.player.setCenterY(SCROLLING_WHILE_PLAYER_IS_DEAD);
		smoothUpdate();
		assertEquals(this.player.getCenterX(), this.fixture.getCurrentScreenX());
		assertEquals(this.player.getCenterY(), this.fixture.getCurrentScreenY());
	}

	@Test
	public void smoothUpdate_givenScreenIsMoreThanOffsetAway_updatesOnlyByScrollLimit() {
		this.player.setCenterX(SCROLLING_WHILE_PLAYER_IS_DEAD * 2);
		this.player.setCenterY(SCROLLING_WHILE_PLAYER_IS_DEAD * 2);
		smoothUpdate();
		// why is this negative?
		assertEquals(-SCROLLING_WHILE_PLAYER_IS_DEAD, this.fixture.getCurrentScreenX());
		assertEquals(-SCROLLING_WHILE_PLAYER_IS_DEAD, this.fixture.getCurrentScreenY());
	}

	public void smoothUpdate() {
		this.fixture.smoothlyUpdateScreenPosition(1);
	}

	@Before
	public void beforeEveryTest() {
		this.player = createOpponentPlayer();
		this.fixture = new CameraPositionCalculation(this.player);

	}
}
