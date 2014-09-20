package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import static de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory.createOpponentPlayer;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.tests.UnitTests;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

@Category(UnitTests.class)
@RunWith(MockitoJUnitRunner.class)
public class PlayerMovementControllerTest {

	private PlayerMovement fixture;

	private Player movedPlayer;
	@Mock
	private CollisionDetection collisionDetection;

	@Test
	public void moveRight_thenPlayerIsFacingRight() {
		whenMovingRight();
		assertFalse(this.movedPlayer.isFacingLeft());
	}

	@Test
	public void moveRight_givenPlayerIsStandingOnGround_thenIsMovingRight() {
		GameObject go = createGameObjectWithGrip(1);
		givenPlayerIsStandingOnGround(go);
		whenMovingRight();
		assertFalse(this.movedPlayer.isMovingLeft());
	}

	@Test
	public void moveLeft_thenPlayerIsMovingLeft() {
		whenMovingLeft();
		assertTrue(this.movedPlayer.isFacingLeft());
	}

	@Test
	public void moveLeft_givenPlayerIsStandingOnGround_thenIsMovingLeft() {
		GameObject go = createGameObjectWithGrip(1);
		givenPlayerIsStandingOnGround(go);
		whenMovingLeft();
		assertTrue(this.movedPlayer.isMovingLeft());
	}

	@Test
	public void moveUp_thenPlayerIsJumping() {
		this.fixture.tryMoveUp();
		assertTrue(this.movedPlayer.getState().isJumpingButtonPressed());
	}

	@Test
	public void moveDown_thenPlayerIsNotJumping() {
		this.fixture.tryMoveDown();
		assertFalse(this.movedPlayer.getState().isJumpingButtonPressed());
	}

	public void whenMovingRight() {
		this.fixture.tryMoveRight();
	}

	public void whenMovingLeft() {
		this.fixture.tryMoveLeft();
	}

	private GameObject createGameObjectWithGrip(int grip) {
		GameObject go = mock(GameObject.class);
		when(go.accelerationOnThisGround()).thenReturn(grip);
		return go;
	}

	private void givenPlayerIsStandingOnGround(GameObject ground) {
		when(this.collisionDetection.findObjectThisPlayerIsStandingOn(this.movedPlayer)).thenReturn(ground);
	}

	@Before
	public void beforeEveryTest() {
		this.movedPlayer = createOpponentPlayer();
		this.fixture = new PlayerMovement(this.movedPlayer);
	}
}