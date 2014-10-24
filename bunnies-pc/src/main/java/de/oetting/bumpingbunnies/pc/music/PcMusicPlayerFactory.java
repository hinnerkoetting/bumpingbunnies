package de.oetting.bumpingbunnies.pc.music;

import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public class PcMusicPlayerFactory {

	public static PcMusicPlayer createBackground(ThreadErrorCallback stopper) {
		PcMusicPlayer player = new PcMusicPlayerFactory().create("/audio/bad_bunnies_2.mp3", stopper);
		player.setOnCompletionListener(new PcOnCompletionListener(() -> player.start()));
		return player;
	}

	public static PcMusicPlayer createJumper(ThreadErrorCallback stopper) {
		return new PcMusicPlayerFactory().create("/audio/jumper.mp3", stopper);
	}

	public static PcMusicPlayer createWater(ThreadErrorCallback stopper) {
		return new PcMusicPlayerFactory().create("/audio/water.mp3", stopper);
	}

	public static PcMusicPlayer createNormalJump(ThreadErrorCallback stopper) {
		return new PcMusicPlayerFactory().create("/audio/normal_jump.mp3", stopper);
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
