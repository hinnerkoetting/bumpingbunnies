package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.oetting.bumpingbunnies.core.game.player.PlayerFactory;
import de.oetting.bumpingbunnies.tests.UnitTests;
import de.oetting.bumpingbunnies.usecases.game.TestableGameObject;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Rect;
import de.oetting.bumpingbunnies.usecases.game.model.Water;

@Category(UnitTests.class)
public class PlayerMovementCalculationTest extends AbstractTestPlayerMovementCalculation {

	@Test
	public void steerAgainstMovement_givenPlayerMovementIsZero_thenMovementShouldBeZeroAfterwards() {
		setHorizontalPlayerMovement(0);
		steerAgainstMovement();
		assertThat(this.player.movementX(), is(equalTo(0)));
	}

	@Test
	public void steerAgainstMovement_givenPlayerMovementIsZero_thenAccelerationXIsAlwaysZero() {
		setHorizontalPlayerMovement(0);
		setHorizontalAcceleration(Integer.MAX_VALUE);
		steerAgainstMovement();
		assertThat(this.player.getAccelerationX(), is(equalTo(0)));
	}

	@Test
	public void steerAgainstMovement_givenMovementSpeedLowerThanAcceleration_thenMovementShouldBeZeroAfterwards() {
		givenAccelerationOnGroundIs(2);
		setHorizontalPlayerMovement(1);
		steerAgainstMovement();
		assertThat(this.player.movementX(), is(equalTo(0)));
	}

	@Test
	public void steerAgainstMovement_givenMovementSpeedIsEqualToAcceleration_thenMovementShouldBeZeroAfterwards() {
		givenAccelerationOnGroundIs(1);
		setHorizontalPlayerMovement(1);
		steerAgainstMovement();
		assertThat(this.player.movementX(), is(equalTo(0)));
	}

	@Test
	public void steerAgainstMovement_givenMovementSpeedIsHigherThanAcceleration_thenAccelerationShouldBeNegativeGroundAcceleration() {
		givenAccelerationOnGroundIs(1);
		setHorizontalPlayerMovement(2);
		steerAgainstMovement();
		assertThat(this.player.getAccelerationX(), is(equalTo(-1)));
	}

	@Test
	public void computeVerticalGravity_givenJumpButtonIsPressedAndNotStanding_thenGravityShouldBeReduced() {
		this.player.getState().setJumpingButtonPressed(true);
		givenPlayerIsInTheAir();
		computeVerticalMovement();
		assertThat(this.player.getAccelerationY(), is(equalTo(ModelConstants.PLAYER_GRAVITY_WHILE_JUMPING)));
	}

	@Test
	public void computeVerticalGravity_givenJumpButtonIsPressedAndStandingOnObject_thanGravityShouldBeZeroAndMovementSpeedIsPositive() {
		this.player.getState().setJumpingButtonPressed(true);
		givenPlayerIsStandingOnGround();
		computeVerticalMovement();
		assertThat(this.player.getAccelerationY(), is(equalTo(0)));
		assertThat(this.player.movementY(), is(equalTo(ModelConstants.PLAYER_JUMP_SPEED)));
	}

	@Test
	public void computeVerticalGravity_givenJumpButtonIsPressedAndInWater_thenGravityIsPostiveAndMovementSpeedIsPositive() {
		this.player.getState().setJumpingButtonPressed(true);
		givenPlayerIsStandingInWater();
		computeVerticalMovement();
		assertThat(this.player.getAccelerationY(), is(equalTo(0)));
		assertThat(this.player.movementY(), is(equalTo(ModelConstants.PLAYER_JUMP_SPEED_WATER)));
	}

	@Test
	public void computeVerticalGravity_givenStandingOnFixedObject_thenGravityShouldBeZero() {
		givenPlayerIsStandingOnGround();
		computeVerticalMovement();
		assertThat(this.player.getAccelerationY(), is(equalTo(0)));
	}

	@Test
	public void computeVerticalGravity_givenPlayerIsFalling_thenGravityShouldBeFallGravity() {
		computeVerticalMovement();
		assertThat(this.player.getAccelerationY(), is(equalTo(ModelConstants.PLAYER_GRAVITY)));
	}

	private void givenPlayerIsStandingInWater() {
		when(this.collisionDetection.findObjectThisPlayerIsCollidingWith(this.player)).thenReturn(new Water(new Rect(), null));
	}

	private void givenPlayerIsStandingOnGround() {
		when(this.collisionDetection.findObjectThisPlayerIsStandingOn(this.player)).thenReturn(new TestableGameObject());
	}

	private void computeVerticalMovement() {
		this.fixture.computeVerticalMovement();
	}

	private void givenPlayerIsInTheAir() {
		when(this.collisionDetection.findObjectThisPlayerIsCollidingWith(this.player)).thenReturn(null);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.player = new PlayerFactory(1).createPlayer(0, "player", TestOpponentFactory.createDummyOpponent());
		this.fixture = new PlayerMovementCalculation(this.player, this.interactionService, this.collisionDetection, this.musicPlayer);
	}
}
