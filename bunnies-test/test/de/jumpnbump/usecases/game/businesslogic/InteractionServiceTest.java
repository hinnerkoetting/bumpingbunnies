package de.jumpnbump.usecases.game.businesslogic;

import static de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory.createPlayerAtPosition;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.oetting.bumpingbunnies.core.world.ObjectProvider;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameObjectInteractor;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;

public class InteractionServiceTest {

	private GameObjectInteractor interactionService;
	private CollisionDetection collisionDetection;
	@Mock
	private ObjectProvider objectProvider;

	// private Wall otherGameObject;

	@Test
	public void interaction_givenPlayerCollidesWithWallOnRight_playerShouldHave0MovementX() {
		Player player = givenPlayerAt00WithXMovement(1);
		givenPlayerStandsDirectlyLeftToWall(player);
		whenPlayerInteractsWithWorld(player);
		assertEquals(
				"Player Movement x must be 0 after it ran into a wall on the right",
				0, player.movementX(), 0.001);
	}

	@Test
	public void interaction_givenPlayerDoesNotCollide_playerShouldRetainMovementX() {
		Player player = createPlayerAtPosition(0, 0);
		player.setMovementX(1);
		whenPlayerInteractsWithWorld(player);
		assertEquals(1, player.movementX(), 0.001);
	}

	@Test
	public void interaction_givenPlayerMovingRightRunningIntoWall_ShouldHaveMomentEqualToDistanceToWall() {
		Player player = givenPlayerAt00WithXMovement(2);
		long wallXPosition = player.maxX() + 1;
		givenCollidingWallAt(wallXPosition, 0);
		whenPlayerInteractsWithWorld(player);
		long distanceToWall = wallXPosition - player.maxX();
		assertEquals(distanceToWall, player.movementX());
	}

	@Test
	public void interaction_givenPlayerMovingLeftRunningIntoWall_ShouldHaveMomentEqualToDistanceToWall() {
		Player player = givenPlayerAt00WithXMovement(-2);
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
		Player player = givenPlayerAt00WithXMovement(1);
		player.setAccelerationX((int) (player.maxX() + 1));
		givenPlayerStandsDirectlyLeftToWall(player);
		whenPlayerInteractsWithWorld(player);
		assertEquals(0, player.getAccelerationX(), 0.001);
	}

	@Test
	public void interaction_givenPlayerMovingLeftIntoWall_shouldHaveNoAccelerationAfterwards() {
		Player player = givenPlayerAt00WithXMovement(-1);
		player.setAccelerationX(-1);
		givenPlayerStandsDirectlyRightToWall(player);
		whenPlayerInteractsWithWorld(player);
		assertEquals(0, player.getAccelerationX(), 0.001);
	}

	@Test
	public void interaction_givenPlayerMovingTopIntoWall_shouldHaveNoAccelerationAfterwards() {
		Player player = givenPlayerAt00WithYMovement(1);
		player.setAccelerationY(1);
		givenPlayerStandsDirectlyUnderWall(player);
		whenPlayerInteractsWithWorld(player);
		assertEquals(0, player.getAccelerationY(), 0.001);
	}

	@Test
	public void interaction_givenPlayerMovingDownIntoWall_shouldHaveNoAccelerationAfterwards() {
		Player player = givenPlayerAt00WithYMovement(-1);
		player.setAccelerationY(-1);
		givenPlayerStandsDirectlyOverWall(player);
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

	@Test
	public void interaction_playerStandingOnWallMovingRight_shouldBeAbleToMoveRight() {
		Player player = givenPlayerAt00WithXMovement(1);
		givenPlayerStandsOnWall(player);
		whenPlayerInteractsWithWorld(player);
		assertEquals(1, player.movementX());
	}

	@Test
	public void interaction_playerStandingOnWallMovingLeft_shouldBeAbleToMoveeft() {
		Player player = givenPlayerAt00WithXMovement(-1);
		givenPlayerStandsOnWall(player);
		whenPlayerInteractsWithWorld(player);
		assertEquals(-1, player.movementX());
	}

	@Test
	public void interaction_playerStandingOnWallMovingRight_shouldHaveNoVerticalMovement() {
		Player player = givenPlayerAt00WithXMovement(1);
		givenPlayerStandsOnWall(player);
		whenPlayerInteractsWithWorld(player);
		assertEquals(0, player.movementY());
	}

	private void givenPlayerStandsOnWall(Player player) {
		Wall otherGameObject = new Wall(-1, player.minX(), player.minY() - 1, player.maxX(), player.minY());
		givenObjectExists(otherGameObject);
	}

	private void givenCollidingWallAt(long x, long y) {
		Wall otherGameObject = new Wall(-1, x, y, x, y);
		givenObjectExists(otherGameObject);
	}

	private Player givenPlayerAt00WithYMovement(int movementY) {
		Player player = createPlayerAtPosition(0, 0);
		player.setMovementY(movementY);
		return player;
	}

	private Player givenPlayerAt00WithXMovement(int movementX) {
		Player player = createPlayerAtPosition(0, 0);
		player.setMovementX(movementX);
		return player;
	}

	private void whenPlayerInteractsWithWorld(Player player) {
		this.interactionService.interactWith(player);
	}

	private void givenPlayerStandsDirectlyUnderWall(Player player) {
		Wall otherGameObject = new Wall(-1, player.minX(), player.maxY(), player.maxX(), player.maxY() + 1);
		givenObjectExists(otherGameObject);
	}

	private void givenPlayerStandsDirectlyLeftToWall(Player player) {
		Wall otherGameObject = new Wall(-1, player.maxX(), player.minY(), player.maxX(), player.maxY());
		givenObjectExists(otherGameObject);
	}

	private void givenPlayerStandsDirectlyOverWall(Player player) {
		Wall otherGameObject = new Wall(-1, player.minX(), player.minY() - 1, player.maxX(), player.minY());
		givenObjectExists(otherGameObject);
	}

	private void givenPlayerStandsDirectlyRightToWall(Player player) {
		Wall otherGameObject = new Wall(-1, player.minX() - 1, player.minY(), player.minX(), player.maxY());
		givenObjectExists(otherGameObject);
	}

	@Before
	public void beforeEveryTest() {
		MockitoAnnotations.initMocks(this);
		this.collisionDetection = new CollisionDetection(this.objectProvider);
		this.interactionService = new GameObjectInteractor(
				this.collisionDetection, this.objectProvider);
	}

	private void givenObjectExists(Wall gameObject) {
		when(this.objectProvider.getAllWalls()).thenReturn(
				Arrays.asList(gameObject));
	}
}
