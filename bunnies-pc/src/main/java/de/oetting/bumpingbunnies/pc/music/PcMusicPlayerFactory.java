package de.oetting.bumpingbunnies.pc.music;

import de.oetting.bumpingbunnies.core.music.DummyMusicPlayer;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.game.BunniesMusicPlayerFactory;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;

public class PcMusicPlayerFactory implements BunniesMusicPlayerFactory {

	private final ThreadErrorCallback errorCallback;

	public PcMusicPlayerFactory(ThreadErrorCallback errorCallback) {
		this.errorCallback = errorCallback;
	}

	public MusicPlayer createBackground() {
		MusicPlayer player = create("/audio/bad_bunnies_2.mp3", errorCallback);
		player.setOnCompletionListener(new PcOnCompletionListener(() -> player.start()));
		return player;
	}

	public MusicPlayer createJumper() {
		return create("/audio/jumper.mp3", errorCallback);
	}

	public MusicPlayer createWater() {
		return create("/audio/water.mp3", errorCallback);
	}

	public MusicPlayer createNormalJump() {
		return create("/audio/normal_jump.mp3", errorCallback);
	}

	public MusicPlayer create(String classpath, ThreadErrorCallback stopper) {
		try {
			if (true)
				return new DummyMusicPlayer();
			PlayerFactory playerFactory = new PlayerFactory(classpath);
			return new PcMusicPlayer(new MusicPlayerThread(stopper, playerFactory));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
