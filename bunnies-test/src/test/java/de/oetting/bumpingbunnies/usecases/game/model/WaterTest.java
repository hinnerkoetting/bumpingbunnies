package de.oetting.bumpingbunnies.usecases.game.model;

import static de.oetting.bumpingbunnies.core.game.TestPlayerFactory.createOpponentPlayer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.movement.CollisionHandling;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.game.objects.Rect;
import de.oetting.bumpingbunnies.model.game.objects.Water;
import de.oetting.bumpingbunnies.tests.UnitTests;

@Category(UnitTests.class)
public class WaterTest {

	private CollisionHandling fixture;

	private Player player;
	private Water water;
	@Mock
	private CollisionDetection collisionDetection;
	@Mock
	private MusicPlayer waterMusic;

	@Test
	public void handleCollision_givenPlayerWasNotInWaterBefore_shouldPlayWaterSound() {
		givenPlayerWasNotInWaterBefore();
		whenPlayerCollidesWithWater();
		verify(this.waterMusic).start();
	}

	private void givenPlayerWasNotInWaterBefore() {
		when(this.collisionDetection.collides(this.water, this.player)).thenReturn(false);
	}

	@Test
	public void handleCollision_givenWasInWaterBefore_shouldNotPlaySoundAgain() {
		givenPlayerWasInWaterBefore();
		whenPlayerCollidesWithWater();
		verifyNoMoreInteractions(this.waterMusic);
	}

	private void whenPlayerCollidesWithWater() {
		this.fixture.interactWith(player, water, this.collisionDetection);
	}

	private void givenPlayerWasInWaterBefore() {
		when(this.collisionDetection.collides(this.water, this.player)).thenReturn(true);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		water = new Water(new Rect(), this.waterMusic);
		this.fixture = new CollisionHandling(mock(MusicPlayer.class), mock(MusicPlayer.class));
		this.player = createOpponentPlayer();
	}
}
