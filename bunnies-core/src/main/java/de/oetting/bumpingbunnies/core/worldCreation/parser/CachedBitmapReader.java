package de.oetting.bumpingbunnies.core.worldCreation.parser;

import java.util.WeakHashMap;

import de.oetting.bumpingbunnies.core.worldCreation.BitmapReader;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

public class CachedBitmapReader implements BitmapReader {

	private final WeakHashMap<String, ImageWrapper> bitmapcache;
	private final BitmapReader delegatedReader;

	public CachedBitmapReader(BitmapReader delegatedReader) {
		super();
		this.delegatedReader = delegatedReader;
		this.bitmapcache = new WeakHashMap<String, ImageWrapper>();
	}

	@Override
	public ImageWrapper readBitmap(String filename) {
		if (this.bitmapcache.containsKey(filename)) {
			return this.bitmapcache.get(filename);
		}
		ImageWrapper bitmap = delegatedReader.readBitmap(filename);
		this.bitmapcache.put(filename, bitmap);
		return bitmap;
	}

}
