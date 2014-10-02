package de.oetting.bumpingbunnies.usecases.game.android.sound;

import android.media.MediaPlayer.OnCompletionListener;
import de.oetting.bumpingbunnies.model.game.objects.OnMusicCompletionListener;

public class AndroidMusicOnCompletionListener implements OnMusicCompletionListener {

	private final OnCompletionListener androidListener;

	public AndroidMusicOnCompletionListener(OnCompletionListener androidListener) {
		super();
		this.androidListener = androidListener;
	}

	public OnCompletionListener getAndroidListener() {
		return androidListener;
	}

}
