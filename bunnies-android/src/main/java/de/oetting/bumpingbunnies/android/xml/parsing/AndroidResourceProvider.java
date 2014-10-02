package de.oetting.bumpingbunnies.android.xml.parsing;

import android.content.Context;
import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.core.worldCreation.BitmapReader;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
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
	public ImageWrapper readBitmap(String fileName) {
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
