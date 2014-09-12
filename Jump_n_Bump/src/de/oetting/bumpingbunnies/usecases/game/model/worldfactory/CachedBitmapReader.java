package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.lang.reflect.Field;
import java.util.WeakHashMap;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.core.worldCreation.BitmapReader;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidBitmap;
import de.oetting.bumpingbunnies.usecases.game.model.Image;

public class CachedBitmapReader implements BitmapReader {

	private final WeakHashMap<String, Image> bitmapcache;
	private final Resources resources;

	public CachedBitmapReader(Resources resources) {
		super();
		this.resources = resources;
		this.bitmapcache = new WeakHashMap<String, Image>();
	}

	@Override
	public Image readBitmap(String filename) {
		if (this.bitmapcache.containsKey(filename)) {
			return this.bitmapcache.get(filename);
		}
		Image bitmap = new AndroidBitmap(BitmapFactory.decodeResource(this.resources, getResourceId(filename)));
		this.bitmapcache.put(filename, bitmap);
		return bitmap;
	}

	private int getResourceId(String filename) {
		try {
			Class<?> res = R.drawable.class;
			Field field = res.getField(filename);
			return field.getInt(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
