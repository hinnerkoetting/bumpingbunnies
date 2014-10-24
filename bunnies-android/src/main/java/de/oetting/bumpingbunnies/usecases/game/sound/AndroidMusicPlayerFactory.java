package de.oetting.bumpingbunnies.usecases.game.sound;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Context;
import android.media.MediaPlayer;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.model.game.BunniesMusicPlayerFactory;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.android.sound.AndroidMusicPlayer;

public class AndroidMusicPlayerFactory implements BunniesMusicPlayerFactory {

	private final Context context;

	public AndroidMusicPlayerFactory(Context context) {
		this.context = context;
	}

	public MusicPlayer createBackground() {
		MediaPlayer music1 = MediaPlayer.create(context, R.raw.bunny_poppig_schnell);
		music1.setVolume(0.5f, 0.5f);
		MediaPlayer music2 = MediaPlayer.create(context, R.raw.bad_bunnies_2);
		music2.setVolume(0.5f, 0.5f);
		return createMultiTrack(Arrays.asList(music1, music2));
	}

	public MusicPlayer createWater() {
		return create(MediaPlayer.create(context, R.raw.water));
	}

	public MusicPlayer createJumper() {
		return create(MediaPlayer.create(context, R.raw.jumper));
	}

	public MusicPlayer createNormalJump() {
		return create(MediaPlayer.create(context, R.raw.normal_jump));
	}

	private static MusicPlayer createMultiTrack(List<MediaPlayer> mediaplayers) {
		return new MultiTrackMusicPlayer(createListOfMusicPlayers(mediaplayers));
	}

	private static List<MusicPlayer> createListOfMusicPlayers(List<MediaPlayer> mediaplayers) {
		List<MusicPlayer> players = new ArrayList<MusicPlayer>(mediaplayers.size());
		for (MediaPlayer mp : mediaplayers) {
			players.add(create(mp));
		}
		return players;
	}

	private static MusicPlayer create(MediaPlayer mediaplayer) {
		return new AndroidMusicPlayer(mediaplayer);
	}
}
