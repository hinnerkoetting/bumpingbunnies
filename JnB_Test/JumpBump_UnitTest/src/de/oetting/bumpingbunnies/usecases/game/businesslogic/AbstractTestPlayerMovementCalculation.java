package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import static org.mockito.Mockito.when;

import org.mockito.Mock;

import de.oetting.bumpingbunnies.usecases.game.TestableGameObject;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayer;

public class AbstractTestPlayerMovementCalculation {

	protected PlayerMovementCalculation fixture;
	protected Player player;
	@Mock
	protected GameObjectInteractor interactionService;
	@Mock
	protected CollisionDetection collisionDetection;
	@Mock
	protected MusicPlayer musicPlayer;

	protected void givenAccelerationOnGroundIs(int acceleration) {
		TestableGameObject playerStandsOn = new TestableGameObject();
		playerStandsOn.accelerationOnThisGround = acceleration;
		when(this.collisionDetection.findObjectThisPlayerIsStandingOn(this.player)).thenReturn(playerStandsOn);
	}

	protected void setHorizontalPlayerMovement(int movement) {
		this.player.setMovementX(movement);
	}

	protected void setHorizontalAcceleration(int acceleration) {
		this.player.setAccelerationX(acceleration);
	}

	protected void steerAgainstMovement() {
		this.fixture.steerAgainstMovement();
	}

}
