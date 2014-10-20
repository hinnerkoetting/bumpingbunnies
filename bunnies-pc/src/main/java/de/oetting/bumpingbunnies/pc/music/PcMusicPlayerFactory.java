package de.oetting.bumpingbunnies.pc.music;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PcMusicPlayerFactory {

	public static PcMusicPlayer create(String classpath) {
		try {
			AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(String.class.getResourceAsStream("/audio/bad_bunnies_2.mp3"));
			Clip clip = AudioSystem.getClip();
			clip.open(audioInputStream);
			clip.start();
			return new PcMusicPlayer(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
