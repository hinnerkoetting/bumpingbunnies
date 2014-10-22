package de.oetting.bumpingbunnies.pc.music;

import javazoom.jl.player.Player;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;

public class PcMusicPlayerFactory {

	public PcMusicPlayer create(String classpath, ThreadErrorCallback stopper) {
		try {
			Player player = new Player(getClass().getResourceAsStream("/audio/bad_bunnies_2.mp3"));
			return new PcMusicPlayer(new MusicPlayerThread(stopper, player));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
