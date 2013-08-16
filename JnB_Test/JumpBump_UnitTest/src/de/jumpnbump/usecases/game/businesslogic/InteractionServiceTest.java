package de.jumpnbump.usecases.game.businesslogic;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.oetting.bumpingbunnies.usecases.game.ObjectProvider;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.InteractionService;
import de.oetting.bumpingbunnies.usecases.game.factories.PlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;

public class InteractionServiceTest {

	private InteractionService interactionService;
	@Mock
	private CollisionDetection collisionDetection;
	@Mock
	private ObjectProvider objectProvider;
	// private Wall otherGameObject;
	private PlayerFactory playerFactory = new PlayerFactory(1);

	@Test
	public void interaction_givenPlayerCollidesWithWallOnRight_playerShouldHave0MovementX() {
		Player player = givenPlayerAt00WithMovement(1);
		givenPlayerStandsDirectlyBeforeWall(player);
		whenPlayerInteractsWithWorld(player);
		assertEquals(
				"Player Movement x must be 0 after it ran into a wall on the right",
				0, player.movementX(), 0.001);
	}

	@Test
	public void interaction_givenPlayerDoesNotCollide_playerShouldRetainMovementX() {
		Player player = this.playerFactory.createPlayerAtPosition(0, 0);
		player.setMovementX(1);
		whenPlayerInteractsWithWorld(player);
		assertEquals(1, player.movementX(), 0.001);
	}

	@Test
	public void interaction_givenPlayerMovingRightRunningIntoWall_ShouldHaveMomentEqualToDistanceToWall() {
		Player player = givenPlayerAt00WithMovement(2);
		long wallXPosition = player.maxX() + 1;
		givenCollidingWallAt(wallXPosition, 0);
		whenPlayerInteractsWithWorld(player);
		long distanceToWall = wallXPosition - player.maxX();
		assertEquals(distanceToWall, player.movementX());
	}

	@Test
	public void interaction_givenPlayerMovingLeftRunningIntoWall_ShouldHaveMomentEqualToDistanceToWall() {
		Player player = givenPlayerAt00WithMovement(-2);
		long wallXPosition = -1;
		givenCollidingWallAt(wallXPosition, 0);
		whenPlayerInteractsWithWorld(player);
		long distanceToWall = wallXPosition - player.minX();
		assertEquals(distanceToWall, player.movementX());

	}

	@Test
	public void interaction_givenPlayerMovingUpRunningIntoWall_ShouldHaveMomentEqualToDistanceToWall() {
		Player player = givenPlayerAt00WithYMovement(2);
		long wallYPosition = player.maxY() + 1;
		givenCollidingWallAt(0, wallYPosition);
		whenPlayerInteractsWithWorld(player);
		long distanceToWall = wallYPosition - player.maxY();
		assertEquals(distanceToWall, player.movementY());
	}

	@Test
	public void interaction_givenPlayerMovingDownRunningIntoWall_ShouldHaveMomentEqualToDistanceToWall() {
		Player player = givenPlayerAt00WithYMovement(-2);
		long wallYPosition = player.minY() - 1;
		givenCollidingWallAt(0, wallYPosition);
		whenPlayerInteractsWithWorld(player);
		long distanceToWall = wallYPosition - player.minY();
		assertEquals(distanceToWall, player.movementY());
	}

	@Test
	public void interaction_givenPlayerMovingRightIntoWall_shouldHaveNoAccelerationAfterwards() {
		Player player = givenPlayerAt00WithMovement(1);
		player.setAccelerationX((int) (player.maxX() + 1));
		givenPlayerStandsDirectlyBeforeWall(player);
		whenPlayerInteractsWithWorld(player);
		assertEquals(0, player.getAccelerationX(), 0.001);
	}

	@Test
	public void interaction_givenPlayerMovingLeftIntoWall_shouldHaveNoAccelerationAfterwards() {
		Player player = givenPlayerAt00WithMovement(-1);
		player.setAccelerationX(-1);
		givenPlayerStandsDirectlyBeforeWall(player);
		whenPlayerInteractsWithWorld(player);
		assertEquals(0, player.getAccelerationX(), 0.001);
	}

	@Test
	public void interaction_givenPlayerMovingTopIntoWall_shouldHaveNoAccelerationAfterwards() {
		Player player = givenPlayerAt00WithYMovement(1);
		player.setAccelerationY(1);
		givenPlayerStandsDirectlyBeforeWall(player);
		whenPlayerInteractsWithWorld(player);
		assertEquals(0, player.getAccelerationY(), 0.001);
	}

	@Test
	public void interaction_givenPlayerMovingDownIntoWall_shouldHaveNoAccelerationAfterwards() {
		Player player = givenPlayerAt00WithYMovement(-1);
		player.setAccelerationY(-1);
		givenPlayerStandsDirectlyBeforeWall(player);
		whenPlayerInteractsWithWorld(player);
		assertEquals(0, player.getAccelerationY(), 0.001);
	}

	@Test
	public void interaction_playerStandingOnTopOfWall_shouldHaveNoAccelerationAndMovementDown() {
		Player player = givenPlayerAt00WithYMovement(-1);
		player.setAccelerationY((int) player.minY());
		givenPlayerStandsOnWall(player);
		whenPlayerInteractsWithWorld(player);
		assertEquals(0, player.movementY());
		assertEquals(0, player.getAccelerationY(), 0.001);
	}

	private void givenPlayerStandsOnWall(Player player) {
		Wall otherGameObject = new Wall(-1, player.minX(), player.minY() - 1, player.maxX(), player.minY());
		when(
				this.collisionDetection.collides(any(GameObject.class),
						any(GameObject.class))).thenReturn(true);
		when(
				this.collisionDetection.isOverOrUnderObject(
						any(GameObject.class), any(GameObject.class)))
				.thenReturn(true);
		givenObjectExists(otherGameObject);
	}

	private void givenCollidingWallAt(long x, long y) {
		Wall otherGameObject = new Wall(-1, x, y, x, y);
		when(
				this.collisionDetection.collides(any(GameObject.class),
						any(GameObject.class))).thenReturn(true);
		when(
				this.collisionDetection.isLeftOrRightToObject(
						any(GameObject.class), any(GameObject.class)))
				.thenReturn(true);
		when(
				this.collisionDetection.isOverOrUnderObject(
						any(GameObject.class), any(GameObject.class)))
				.thenReturn(true);
		givenObjectExists(otherGameObject);
	}

	private Player givenPlayerAt00WithYMovement(int movementY) {
		Player player = this.playerFactory.createPlayerAtPosition(0, 0);
		player.setMovementY(movementY);
		return player;
	}

	private Player givenPlayerAt00WithMovement(int movementX) {
		Player player = this.playerFactory.createPlayerAtPosition(0, 0);
		player.setMovementX(movementX);
		return player;
	}

	private void whenPlayerInteractsWithWorld(Player player) {
		this.interactionService.interactWith(player, this.objectProvider);
	}

	private void givenPlayerStandsDirectlyBeforeWall(Player player) {
		Wall otherGameObject = new Wall(-1, player.maxX(), player.minY(), player.maxX(), player.maxY());
		when(
				this.collisionDetection.collides(any(GameObject.class),
						any(GameObject.class))).thenReturn(true);
		when(
				this.collisionDetection.isLeftOrRightToObject(
						any(GameObject.class), any(GameObject.class)))
				.thenReturn(true);
		when(
				this.collisionDetection.isOverOrUnderObject(
						any(GameObject.class), any(GameObject.class)))
				.thenReturn(true);
		givenObjectExists(otherGameObject);
	}

	@Before
	public void beforeEveryTest() {
		MockitoAnnotations.initMocks(this);
		this.interactionService = new InteractionService(
				this.collisionDetection);
	}

	private void givenObjectExists(Wall gameObject) {
		when(this.objectProvider.getAllObjects()).thenReturn(
				Arrays.asList((GameObject) gameObject));
	}
}
