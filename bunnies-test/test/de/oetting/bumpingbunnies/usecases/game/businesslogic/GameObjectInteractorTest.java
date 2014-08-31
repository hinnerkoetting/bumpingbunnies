package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.world.ObjectProvider;
import de.oetting.bumpingbunnies.usecases.game.dummy.sound.DummyMusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.factories.PlayerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;
import de.oetting.bumpingbunnies.usecases.game.model.Water;

public class GameObjectInteractorTest {

	@Mock
	private ObjectProvider objectProvider;
	private CollisionDetection collisionDetection = new CollisionDetection(
			objectProvider);
	private Player player;

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
		Water water = new Water(-ModelConstants.PLAYER_WIDTH,
				-ModelConstants.PLAYER_HEIGHT, ModelConstants.PLAYER_WIDTH,
				-ModelConstants.PLAYER_HEIGHT / 2, new DummyMusicPlayer());
		when(objectProvider.getAllWaters()).thenReturn(Arrays.asList(water));
	}

	private void givenPlayerMovesToRight() {
		createPlayerAtCenterOfWorld();
		player.setMovementX(1);
	}

	private void createPlayerAtCenterOfWorld() {
		player = new PlayerFactory(1).createPlayer(-1, "test",
				Opponent.createMyPlayer(""));
		player.setCenterX(0);
		player.setCenterY(0);
	}

	private void givenWallIsRightOfPlayer() {
		Wall wall = new Wall(1, ModelConstants.PLAYER_WIDTH / 2, 0,
				ModelConstants.PLAYER_WIDTH, 1);
		when(objectProvider.getAllWalls()).thenReturn(Arrays.asList(wall));
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
		return new GameObjectInteractor(collisionDetection, objectProvider);
	}

	@Before
	public void setup() {
		initMocks(this);
	}

}
