package de.oetting.bumpingbunnies.android.xml.parsing;

import java.io.InputStream;

import android.graphics.BitmapFactory;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.worldcreator.load.BitmapReader;
import de.oetting.bumpingbunnies.worldcreator.load.ClasspathImageReader;

public class AndroidBitmapReader implements BitmapReader {

	private final ClasspathImageReader imageReader;

	public AndroidBitmapReader() {
		this.imageReader = new ClasspathImageReader();
	}

	@Override
	public ImageWrapper readBitmap(String filename) {
		return readBitmap(imageReader.readAsStream(filename), filename);
	}

	public ImageWrapper readBitmap(InputStream stream, String filename) {
		return new ImageWrapper(BitmapFactory.decodeStream(stream), filename);
	}
}
