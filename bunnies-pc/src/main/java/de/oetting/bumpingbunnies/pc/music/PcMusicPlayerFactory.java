package de.oetting.bumpingbunnies.pc.music;

import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.game.BunniesMusicPlayerFactory;

public class PcMusicPlayerFactory implements BunniesMusicPlayerFactory {

	private final ThreadErrorCallback errorCallback;

	public PcMusicPlayerFactory(ThreadErrorCallback errorCallback) {
		this.errorCallback = errorCallback;
	}

	public PcMusicPlayer createBackground() {
		PcMusicPlayer player = create("/audio/bad_bunnies_2.mp3", errorCallback);
		player.setOnCompletionListener(new PcOnCompletionListener(() -> player.start()));
		return player;
	}

	public PcMusicPlayer createJumper() {
		return create("/audio/jumper.mp3", errorCallback);
	}

	public PcMusicPlayer createWater() {
		return create("/audio/water.mp3", errorCallback);
	}

	public PcMusicPlayer createNormalJump() {
		return create("/audio/normal_jump.mp3", errorCallback);
	}

	public PcMusicPlayer create(String classpath, ThreadErrorCallback stopper) {
		try {
			PlayerFactory playerFactory = new PlayerFactory(classpath);
			return new PcMusicPlayer(new MusicPlayerThread(stopper, playerFactory));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
