package de.oetting.bumpingbunnies.usecases.game.model;

import static de.oetting.bumpingbunnies.usecases.game.businesslogic.TestPlayerFactory.createOpponentPlayer;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.tests.UnitTests;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionHandling;
import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;

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
		when(this.collisionDetection.collides(this.water, this.player))
				.thenReturn(false);
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
		when(this.collisionDetection.collides(this.water, this.player))
				.thenReturn(true);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		water = new Water(new Rect(), this.waterMusic);
		this.fixture = new CollisionHandling();
		this.player = createOpponentPlayer();
	}
}
