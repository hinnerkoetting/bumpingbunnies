package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.oetting.bumpingbunnies.core.game.player.PlayerFactory;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class PlayerMovementWithHigherSpeedTest extends AbstractTestPlayerMovementCalculation {

	private static final int speed = 2;

	@Test
	public void steerAgainstMovement_givenMovementIsEqualToNextAcceleration_thenMovementShouldBeZero() {
		setHorizontalPlayerMovement(4);
		givenAccelerationOnGroundIs(2);
		steerAgainstMovement();
		assertThat(this.player.movementX(), is(equalTo(0)));
	}

	@Test
	public void steerAgainstMovement_givenMovementIsEqualToNextAcceleration_thenAccelerationShouldBeZero() {
		setHorizontalPlayerMovement(4);
		givenAccelerationOnGroundIs(2);
		steerAgainstMovement();
		assertThat(this.player.getAccelerationX(), is(equalTo(0)));
	}

	@Test
	public void steerAgainstMovement_givenMovementIsHigherThanNextAcceleration_thenGroundAccelerationIsAppliedNegative() {
		setHorizontalPlayerMovement(5);
		givenAccelerationOnGroundIs(2);
		steerAgainstMovement();
		assertThat(this.player.getAccelerationX(), is(equalTo(-8)));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.player = new PlayerFactory(speed).createPlayer(0, "player", null);
		this.fixture = new PlayerMovementCalculation(this.player, this.interactionService, this.collisionDetection, this.musicPlayer);
	}
}
