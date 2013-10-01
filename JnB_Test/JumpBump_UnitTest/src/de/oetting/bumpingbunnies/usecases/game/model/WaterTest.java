package de.oetting.bumpingbunnies.usecases.game.model;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayer;

public class WaterTest {

	private Water fixture;

	private Player player;
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
		when(this.collisionDetection.collides(this.fixture, this.player)).thenReturn(false);
	}

	@Test
	public void handleCollision_givenWasInWaterBefore_shouldNotPlaySoundAgain() {
		givenPlayerWasInWaterBefore();
		whenPlayerCollidesWithWater();
		verifyNoMoreInteractions(this.waterMusic);
	}

	private void whenPlayerCollidesWithWater() {
		this.fixture.handleCollisionWithPlayer(this.player, this.collisionDetection);
	}

	private void givenPlayerWasInWaterBefore() {
		when(this.collisionDetection.collides(this.fixture, this.player)).thenReturn(true);
	}

	@Before
	public void beforeEveryTest() {
		initMocks(this);
		this.fixture = new Water(new Rect(), this.waterMusic);
		this.player = new Player(0, "", 1);
	}
}
