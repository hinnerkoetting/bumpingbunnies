package de.oetting.bumpingbunnies.core.game.movement;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;

import de.oetting.bumpingbunnies.core.game.OpponentTestFactory;
import de.oetting.bumpingbunnies.core.game.player.BunnyFactory;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Water;
import de.oetting.bumpingbunnies.tests.UnitTests;
import de.oetting.bumpingbunnies.usecases.game.TestableGameObject;

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
		assertThat(this.player.getAccelerationY(), is(equalTo(ModelConstants.BUNNY_GRAVITY_WHILE_JUMPING)));
	}

	@Test
	public void computeVerticalGravity_givenJumpButtonIsPressedAndStandingOnObject_thanGravityShouldBeZeroAndMovementSpeedIsPositive() {
		this.player.getState().setJumpingButtonPressed(true);
		givenPlayerIsStandingOnGround();
		computeVerticalMovement();
		assertThat(this.player.getAccelerationY(), is(equalTo(0)));
		assertThat(this.player.movementY(), is(equalTo(ModelConstants.BUNNY_JUMP_SPEED)));
	}

	@Test
	public void computeVerticalGravity_givenJumpButtonIsPressedAndInWater_thenGravityIsZeroAndMovementSpeedIsPositive() {
		this.player.getState().setJumpingButtonPressed(true);
		givenPlayerIsStandingInWater();
		computeVerticalMovement();
//		assertThat(this.player.getAccelerationY(), is(equalTo(0)));
		assertThat(this.player.movementY(), is(equalTo(ModelConstants.BUNNY_JUMP_SPEED_WATER)));
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
		assertThat(this.player.getAccelerationY(), is(equalTo(ModelConstants.BUNNY_GRAVITY)));
	}

	private void givenPlayerIsStandingInWater() {
		when(this.collisionDetection.findObjectThisPlayerIsCollidingWith(this.player)).thenReturn(new Water(0, 0, 0, 0, 0));
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
		this.player = new BunnyFactory(1).createPlayer(0, "player", OpponentTestFactory.create());
		this.fixture = new PlayerMovementCalculation(this.player, this.interactionService, this.collisionDetection, this.musicPlayer);
	}
}
