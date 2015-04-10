package de.oetting.bumpingbunnies.core.game.graphics;

import org.junit.Test;

public class PlayerImagesReaderTest {

	@Test
	public void loadAllRunning_smokeTest() {
		new BunnyImagesReader().loadAllRunningImages();
	}

	@Test
	public void loadAllJumping_smokeTest() {
		new BunnyImagesReader().loadAllJumpingImages();
	}

	@Test
	public void loadAllFalling_smokeTest() {
		new BunnyImagesReader().loadAllFallingImages();
	}

	@Test
	public void loadAllJumpingUp_smokeTest() {
		new BunnyImagesReader().loadAllJumpingUpImages();
	}

	@Test
	public void loadAllSitting_smokeTest() {
		new BunnyImagesReader().loadAllSittingImages();
	}

}
