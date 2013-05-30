package de.jumpnbump.usecases.game.businesslogic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.jumpnbump.usecases.game.ObjectProvider;
import de.jumpnbump.usecases.game.factories.PlayerFactory;
import de.jumpnbump.usecases.game.model.GameObject;
import de.jumpnbump.usecases.game.model.Player;

public class InteractionServiceTest {

	private InteractionService interactionService;
	@Mock
	private CollisionDetection collisionDetection;
	@Mock
	private ObjectProvider objectProvider;
	@Mock
	private GameObject otherGameObject;

	@Test
	public void interaction_givenNoCollision_NothingShouldHappen() {
		// ???
	}

	@Test
	public void interaction_givenPlayerCollidesWithWallOnRight_playerShouldHave0MovementX() {
		Player player = givenPlayerAt00WithMovement(1);
		givenPlayerStandsDirectoyBeforeWall(player);
		whenPlayerInteractsWithWorld(player);
		assertEquals(
				"Player Movement x must be 0 after it ran into a wall on the right",
				0, player.movementX(), 0.001);
	}

	@Test
	public void interaction_givenPlayerDoesNotCollide_playerShouldRetainMovementX() {
		Player player = PlayerFactory.createPlayerAtPosition(0, 0);
		player.setMovementX(1);
		whenPlayerInteractsWithWorld(player);
		assertEquals(1, player.movementX(), 0.001);
	}

	@Test
	public void interaction_givenPlayerMovingRightRunningIntoWall_ShouldHaveMomentEqualToDistanceToWall() {
		Player player = givenPlayerAt00WithMovement(1);
		double wallXPosition = 0.5;
		givenCollidingWallAt(wallXPosition, 0);
		whenPlayerInteractsWithWorld(player);
		double distanceToWall = wallXPosition - player.maxX();
		assertEquals(distanceToWall, player.movementX(), 0.001);
	}

	@Test
	public void interaction_givenPlayerMovingLeftRunningIntoWall_ShouldHaveMomentEqualToDistanceToWall() {
		Player player = givenPlayerAt00WithMovement(-1);
		double wallXPosition = -0.5;
		givenCollidingWallAt(wallXPosition, 0);
		whenPlayerInteractsWithWorld(player);
		double distanceToWall = wallXPosition - player.minX();
		assertEquals(distanceToWall, player.movementX(), 0.001);

	}

	@Test
	public void interaction_givenPlayerMovingUpRunningIntoWall_ShouldHaveMomentEqualToDistanceToWall() {
		Player player = givenPlayerAt00WithYMovement(1);
		double wallYPosition = 0.5;
		givenCollidingWallAt(0, wallYPosition);
		whenPlayerInteractsWithWorld(player);
		double distanceToWall = wallYPosition - player.maxY();
		assertEquals(distanceToWall, player.movementY(), 0.001);
	}

	@Test
	public void interaction_givenPlayerMovingDownRunningIntoWall_ShouldHaveMomentEqualToDistanceToWall() {
		Player player = givenPlayerAt00WithYMovement(-1);
		double wallYPosition = -0.5;
		givenCollidingWallAt(0, wallYPosition);
		whenPlayerInteractsWithWorld(player);
		double distanceToWall = wallYPosition - player.minY();
		assertEquals(distanceToWall, player.movementY(), 0.001);
	}

	private void givenCollidingWallAt(double x, double y) {
		when(
				this.collisionDetection.collides(any(GameObject.class),
						any(GameObject.class))).thenReturn(true);
		when(this.otherGameObject.maxX()).thenReturn(x);
		when(this.otherGameObject.minX()).thenReturn(x);
		when(this.otherGameObject.minY()).thenReturn(y);
		when(this.otherGameObject.maxY()).thenReturn(y);
	}

	private Player givenPlayerAt00WithYMovement(double movementY) {
		Player player = PlayerFactory.createPlayerAtPosition(0, 0);
		player.setMovementY(movementY);
		return player;
	}

	private Player givenPlayerAt00WithMovement(double movementX) {
		Player player = PlayerFactory.createPlayerAtPosition(0, 0);
		player.setMovementX(movementX);
		return player;
	}

	private void whenPlayerInteractsWithWorld(Player player) {
		this.interactionService.interactWith(player, this.objectProvider);
	}

	private void givenPlayerStandsDirectoyBeforeWall(Player player) {
		when(
				this.collisionDetection.collides(any(GameObject.class),
						any(GameObject.class))).thenReturn(true);
		when(this.otherGameObject.maxX()).thenReturn(player.minX());
		when(this.otherGameObject.minX()).thenReturn(player.maxX());
		when(this.otherGameObject.maxY()).thenReturn(player.minY());
		when(this.otherGameObject.minY()).thenReturn(player.maxY());
	}

	@Before
	public void beforeEveryTest() {
		MockitoAnnotations.initMocks(this);
		this.interactionService = new InteractionService(
				this.collisionDetection);
		initObjectProviderwithOneObject();
	}

	private void initObjectProviderwithOneObject() {
		when(this.objectProvider.getAllObjects()).thenReturn(
				Arrays.asList(this.otherGameObject));
	}
}
