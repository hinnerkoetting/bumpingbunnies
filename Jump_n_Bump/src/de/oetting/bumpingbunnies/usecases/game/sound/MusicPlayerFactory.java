package de.oetting.bumpingbunnies.usecases.game.sound;

import android.content.Context;
import android.media.MediaPlayer;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.game.android.sound.AndroidMusicPlayer;

public class MusicPlayerFactory {

	public static MusicPlayer createBackground(Context context) {
		MediaPlayer backgroundMusic = MediaPlayer.create(context, R.raw.bad_bunnies_2);
		backgroundMusic.setLooping(true);
		return create(backgroundMusic);
	}

	public static MusicPlayer createWater(Context context) {
		return create(MediaPlayer.create(context, R.raw.water));
	}

	private static MusicPlayer create(MediaPlayer mediaplayer) {
		return new AndroidMusicPlayer(mediaplayer);
	}
}
