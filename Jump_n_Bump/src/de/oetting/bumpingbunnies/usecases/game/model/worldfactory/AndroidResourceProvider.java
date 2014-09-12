package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import android.content.Context;
import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.core.worldCreation.BitmapReader;
import de.oetting.bumpingbunnies.usecases.game.model.Image;
import de.oetting.bumpingbunnies.usecases.game.music.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayerFactory;

public class AndroidResourceProvider implements ResourceProvider {

	private final BitmapReader bitmapReader;
	private final Context context;

	public AndroidResourceProvider(BitmapReader bitmapReader, Context context) {
		super();
		this.bitmapReader = bitmapReader;
		this.context = context;
	}

	@Override
	public Image readBitmap(String fileName) {
		return bitmapReader.readBitmap(fileName);
	}

	@Override
	public MusicPlayer readerJumperMusic() {
		return MusicPlayerFactory.createJumper(context);
	}

	@Override
	public MusicPlayer readWaterMusic() {
		return MusicPlayerFactory.createWater(context);
	}

}
