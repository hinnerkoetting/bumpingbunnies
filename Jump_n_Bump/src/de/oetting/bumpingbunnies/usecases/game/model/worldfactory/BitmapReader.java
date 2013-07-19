package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.lang.reflect.Field;
import java.util.WeakHashMap;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import de.oetting.bumpingbunnies.R;

public class BitmapReader {

	private final WeakHashMap<String, Bitmap> bitmapcache;
	private final Resources resources;

	public BitmapReader(Resources resources) {
		super();
		this.resources = resources;
		this.bitmapcache = new WeakHashMap<String, Bitmap>();
	}

	public Bitmap readBitmap(String filename) {
		if (this.bitmapcache.containsKey(filename)) {
			return this.bitmapcache.get(filename);
		}
		Bitmap bitmap = BitmapFactory.decodeResource(this.resources, getResourceId(filename));
		this.bitmapcache.put(filename, bitmap);
		return bitmap;
	}

	private int getResourceId(String id) {
		try {
			Class<?> res = R.drawable.class;
			Field field = res.getField("blumenwiese21");
			return field.getInt(null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
