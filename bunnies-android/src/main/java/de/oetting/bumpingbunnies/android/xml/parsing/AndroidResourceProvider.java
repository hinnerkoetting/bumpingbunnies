package de.oetting.bumpingbunnies.android.xml.parsing;

import android.content.Context;
import de.oetting.bumpingbunnies.core.music.DummyMusicPlayer;
import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.core.worldCreation.BitmapReader;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.game.sound.AndroidMusicPlayerFactory;

public class AndroidResourceProvider implements ResourceProvider {

	private final BitmapReader bitmapReader;
	private final AndroidMusicPlayerFactory musicPlayerFactory;
	private final LocalSettings settings;

	public AndroidResourceProvider(BitmapReader bitmapReader, Context context, LocalSettings settings) {
		this.bitmapReader = bitmapReader;
		this.musicPlayerFactory = new AndroidMusicPlayerFactory(context);
		this.settings = settings;
	}

	@Override
	public ImageWrapper readBitmap(String fileName) {
		return bitmapReader.readBitmap(fileName);
	}

	@Override
	public MusicPlayer readerJumperMusic() {
		if (settings.isPlaySounds())
			return musicPlayerFactory.createJumper();
		return new DummyMusicPlayer();
	}

	@Override
	public MusicPlayer readWaterMusic() {
		if (settings.isPlaySounds())
			return musicPlayerFactory.createWater();
		return new DummyMusicPlayer();
	}

}
