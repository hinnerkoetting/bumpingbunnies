package de.oetting.bumpingbunnies.core.game.graphics;

import org.junit.Test;

public class PlayerImagesReaderTest {

	@Test
	public void loadAllRunning_smokeTest() {
		new PlayerImagesReader().loadAllRunningImages();
	}

	@Test
	public void loadAllJumping_smokeTest() {
		new PlayerImagesReader().loadAllJumpingImages();
	}

	@Test
	public void loadAllFalling_smokeTest() {
		new PlayerImagesReader().loadAllFallingImages();
	}

	@Test
	public void loadAllJumpingUp_smokeTest() {
		new PlayerImagesReader().loadAllJumpingUpImages();
	}

	@Test
	public void loadAllSitting_smokeTest() {
		new PlayerImagesReader().loadAllSittingImages();
	}

}
