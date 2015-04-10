package de.oetting.bumpingbunnies.pc.music;

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
//		MusicPlayer player = create("/audio/bunny_poppig_schnell.mp3", errorCallback);
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
	
	@Override
	public MusicPlayer createDeadPlayer() {
		return create("/audio/sprung_bunny2bunny.mp3", errorCallback);
	}

	public MusicPlayer create(String classpath, ThreadErrorCallback stopper) {
		try {
			BunnyFactory playerFactory = new BunnyFactory(classpath);
			return new PcMusicPlayer(new MusicPlayerThread(stopper, playerFactory));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	
}
