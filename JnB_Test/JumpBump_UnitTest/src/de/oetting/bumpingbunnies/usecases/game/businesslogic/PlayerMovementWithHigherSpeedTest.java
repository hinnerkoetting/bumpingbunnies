package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;

import de.oetting.bumpingbunnies.usecases.game.factories.PlayerFactory;

public class PlayerMovementWithHigherSpeedTest extends AbstractTestPlayerMovementCalculation {

	private static final int speed = 2;

	@Test
	public void steerAgainstMovement_givenMovementIsEqualToAccelerationMultipliedWithSpeedFactor_thenMovementShouldBeEqualToBefore() {
		setHorizontalPlayerMovement(2);
		givenAccelerationOnGroundIs(1);
		steerAgainstMovement();
		assertThat(this.player.movementX(), is(equalTo(2)));
	}

	@Test
	public void steerAgainstMovement_givenMovementIsEqualToAccelerationMultipliedWithSpeedFactor_thenAccelerationShouldBeNegativeGroundAcceleration() {
		setHorizontalPlayerMovement(2);
		givenAccelerationOnGroundIs(1);
		steerAgainstMovement();
		assertThat(this.player.getAccelerationX(), is(equalTo(-1)));
	}

	@Test
	public void steerAgainstMovement_givenMovementIsHigherThanAccelerationMultSpeed_thenAccelerationShouldBeNegativeGroundAcceleration() {
		setHorizontalPlayerMovement(3);
		givenAccelerationOnGroundIs(1);
		steerAgainstMovement();
		assertThat(this.player.getAccelerationX(), is(equalTo(-1)));
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.player = new PlayerFactory(speed).createPlayer(0, "player");
		this.fixture = new PlayerMovementCalculation(this.player, this.interactionService, this.collisionDetection);
	}
}
