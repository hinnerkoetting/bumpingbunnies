package de.oetting.bumpingbunnies.usecases.game.sound;

import android.content.Context;
import android.media.MediaPlayer;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.game.android.sound.AndroidMusicPlayer;

public class MusicPlayerFactory {

	public static MusicPlayer createBackground(Context context) {
		MediaPlayer backgroundMusic = MediaPlayer.create(context, R.raw.bad_bunnies_2);
		backgroundMusic.setLooping(true);
		backgroundMusic.setVolume(0.5f, 0.5f);
		return create(backgroundMusic);
	}

	public static MusicPlayer createWater(Context context) {
		return create(MediaPlayer.create(context, R.raw.water));
	}

	public static MusicPlayer createJumper(Context context) {
		return create(MediaPlayer.create(context, R.raw.jumper));
	}

	public static MusicPlayer createNormalJump(Context context) {
		return create(MediaPlayer.create(context, R.raw.normal_jump));
	}

	private static MusicPlayer create(MediaPlayer mediaplayer) {
		return new AndroidMusicPlayer(mediaplayer);
	}
}
