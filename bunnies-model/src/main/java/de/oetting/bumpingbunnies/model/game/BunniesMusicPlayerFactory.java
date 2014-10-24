package de.oetting.bumpingbunnies.model.game;

public interface BunniesMusicPlayerFactory {

	MusicPlayer createBackground();

	MusicPlayer createJumper();

	MusicPlayer createNormalJump();

	MusicPlayer createWater();
}
