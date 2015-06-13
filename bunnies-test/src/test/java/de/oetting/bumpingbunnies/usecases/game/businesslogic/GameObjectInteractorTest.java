package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.game.OpponentTestFactory;
import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.CollisionHandling;
import de.oetting.bumpingbunnies.core.game.movement.GameObjectInteractor;
import de.oetting.bumpingbunnies.core.game.player.BunnyFactory;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;
import de.oetting.bumpingbunnies.model.game.world.ObjectProvider;

public class GameObjectInteractorTest {

	@Mock
	private ObjectProvider objectProvider;
	private CollisionDetection collisionDetection = new CollisionDetection(objectProvider);
	private Bunny player;

	@Test
	public void interactWith_playerMovesIntoWall_reducesMovementToZero() {
		givenPlayerMovesToRight();
		givenWallIsRightOfPlayer();
		whenCalculatingInteractions();
		thenPlayerIsNotMovingAnymore();
	}

	@Test
	public void interactWith_playerFallsIntoWater_reducesSpeedByOnePercent() {
		givenPlayerMovesDown();
		givenWaterIsBelowPlayer();
		whenCalculatingInteractions();
		thenPlayerMovingSlower();
	}

	private void givenPlayerMovesDown() {
		createPlayerAtCenterOfWorld();
		player.setMovementY(-100);
	}

	private void givenWaterIsBelowPlayer() {
		Water water = new Water(-1, -ModelConstants.BUNNY_GAME_WIDTH, -ModelConstants.BUNNY_GAME_HEIGHT, ModelConstants.BUNNY_GAME_WIDTH, -ModelConstants.BUNNY_GAME_HEIGHT / 2);
		when(objectProvider.getAllWaters()).thenReturn(Arrays.asList(water));
		when(objectProvider.getCandidateForCollisionWater(any(Bunny.class))).thenReturn(Arrays.asList(water));
	}

	private void givenPlayerMovesToRight() {
		createPlayerAtCenterOfWorld();
		player.setMovementX(1);
	}

	private void createPlayerAtCenterOfWorld() {
		player = new BunnyFactory(1).createPlayer(-1, "test", OpponentTestFactory.create());
		player.setCenterX(0);
		player.setCenterY(0);
	}

	private void givenWallIsRightOfPlayer() {
		Wall wall = new Wall(1, ModelConstants.BUNNY_GAME_WIDTH / 2, 0, ModelConstants.BUNNY_GAME_WIDTH, 1);
		when(objectProvider.getAllWalls()).thenReturn(Arrays.asList(wall));
		when(objectProvider.getCandidateForCollisionWalls(any(Bunny.class))).thenReturn(Arrays.asList(wall));
	}

	private void whenCalculatingInteractions() {
		GameObjectInteractor interactor = createInteractor();
		interactor.interactWith(player);
	}

	private void thenPlayerIsNotMovingAnymore() {
		assertThat(player.getState().getMovementX(), is(0));
		assertThat(player.getState().getMovementY(), is(0));
	}

	private void thenPlayerMovingSlower() {
		assertThat(player.getState().getMovementY(), is(-99));
	}

	private GameObjectInteractor createInteractor() {
		return new GameObjectInteractor(collisionDetection, objectProvider, new CollisionHandling(mock(MusicPlayer.class), mock(MusicPlayer.class)));
	}

	@Before
	public void setup() {
		initMocks(this);
	}

}
