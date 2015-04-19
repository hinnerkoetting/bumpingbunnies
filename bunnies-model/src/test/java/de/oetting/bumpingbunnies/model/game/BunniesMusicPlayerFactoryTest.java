package de.oetting.bumpingbunnies.model.game;

import org.junit.Before;
import org.junit.Test;

public abstract class BunniesMusicPlayerFactoryTest {

	private BunniesMusicPlayerFactory factory;
	
	@Test
	public void backgroundCanBePlayedWithoutError() {
		MusicPlayer background = factory.createBackground();
		verifyThatMusicCanBePlayed(background);
	}
	
	@Test
	public void deadPlayerCanBePlayedWithoutError() {
		MusicPlayer music = factory.createDeadPlayer();
		verifyThatMusicCanBePlayed(music);
	}
	@Test
	public void jumperCanBePlayedWithoutError() {
		MusicPlayer music = factory.createJumper();
		verifyThatMusicCanBePlayed(music);
	}
	
	@Test
	public void normalJumpCanBePlayedWithoutError() {
		MusicPlayer music = factory.createNormalJump();
		verifyThatMusicCanBePlayed(music);
	}
	
	@Test
	public void waterCanBePlayedWithoutError() {
		MusicPlayer music = factory.createWater();
		verifyThatMusicCanBePlayed(music);
	}

	private void verifyThatMusicCanBePlayed(MusicPlayer player) {
		player.start();
		player.stopBackground();
	}
	
	@Before
	public void setup() {
		factory = createFactory();
	}

	protected abstract BunniesMusicPlayerFactory createFactory();

}
