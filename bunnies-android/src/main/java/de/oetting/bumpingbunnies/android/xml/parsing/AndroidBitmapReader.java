package de.oetting.bumpingbunnies.android.xml.parsing;

import android.graphics.BitmapFactory;
import de.oetting.bumpingbunnies.core.worldCreation.BitmapReader;
import de.oetting.bumpingbunnies.core.worldCreation.ClasspathImageReader;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class AndroidBitmapReader implements BitmapReader {

	private final ClasspathImageReader imageReader;

	public AndroidBitmapReader() {
		this.imageReader = new ClasspathImageReader();
	}

	@Override
	public ImageWrapper readBitmap(String filename) {
		return new ImageWrapper(BitmapFactory.decodeStream(imageReader.readAsStream(filename)), filename);
	}
}
