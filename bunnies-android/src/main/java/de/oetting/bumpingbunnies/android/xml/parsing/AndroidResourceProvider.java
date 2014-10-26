package de.oetting.bumpingbunnies.android.xml.parsing;

import de.oetting.bumpingbunnies.core.resources.ResourceProvider;
import de.oetting.bumpingbunnies.core.worldCreation.BitmapReader;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class AndroidResourceProvider implements ResourceProvider {

	private final BitmapReader bitmapReader;

	public AndroidResourceProvider(BitmapReader bitmapReader) {
		this.bitmapReader = bitmapReader;
	}

	@Override
	public ImageWrapper readBitmap(String fileName) {
		return bitmapReader.readBitmap(fileName);
	}

}
