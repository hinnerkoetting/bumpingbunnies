package de.oetting.bumpingbunnies.android.xml.parsing;

import android.content.Context;
import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.core.worldCreation.BitmapReader;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.game.sound.AndroidMusicPlayerFactory;

public class AndroidResourceProvider implements ResourceProvider {

	private final BitmapReader bitmapReader;
	private final AndroidMusicPlayerFactory musicPlayerFactory;

	public AndroidResourceProvider(BitmapReader bitmapReader, Context context) {
		this.bitmapReader = bitmapReader;
		musicPlayerFactory = new AndroidMusicPlayerFactory(context);
	}

	@Override
	public ImageWrapper readBitmap(String fileName) {
		return bitmapReader.readBitmap(fileName);
	}

	@Override
	public MusicPlayer readerJumperMusic() {
		return musicPlayerFactory.createJumper();
	}

	@Override
	public MusicPlayer readWaterMusic() {
		return musicPlayerFactory.createWater();
	}

}
