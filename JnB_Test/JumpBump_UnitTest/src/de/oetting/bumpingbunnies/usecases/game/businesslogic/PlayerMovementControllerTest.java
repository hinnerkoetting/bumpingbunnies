package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

@RunWith(MockitoJUnitRunner.class)
public class PlayerMovementControllerTest {

	private PlayerMovementController fixture;

	private Player movedPlayer;
	@Mock
	private InteractionService interActionService;
	@Mock
	private CollisionDetection collisionDetection;

	@Test
	public void moveRight_thenPlayerIsFacingRight() {
		this.fixture.tryMoveRight();
		assertFalse(this.movedPlayer.isFacingLeft());
	}

	@Test
	public void moveLeft_thenPlayerIsMovingLeft() {
		this.fixture.tryMoveLeft();
		assertTrue(this.movedPlayer.isFacingLeft());
	}

	@Before
	public void beforeEveryTest() {
		this.movedPlayer = new Player(-1, "any", 1);
		this.fixture = new PlayerMovementController(this.movedPlayer, this.interActionService, this.collisionDetection);
	}
}
