package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.util.WeakHashMap;

import de.oetting.bumpingbunnies.core.worldCreation.BitmapReader;
import de.oetting.bumpingbunnies.usecases.game.model.Image;

public class CachedBitmapReader implements BitmapReader {

	private final WeakHashMap<String, Image> bitmapcache;
	private final BitmapReader delegatedReader;

	public CachedBitmapReader(BitmapReader delegatedReader) {
		super();
		this.delegatedReader = delegatedReader;
		this.bitmapcache = new WeakHashMap<String, Image>();
	}

	@Override
	public Image readBitmap(String filename) {
		if (this.bitmapcache.containsKey(filename)) {
			return this.bitmapcache.get(filename);
		}
		Image bitmap = delegatedReader.readBitmap(filename);
		this.bitmapcache.put(filename, bitmap);
		return bitmap;
	}

}
