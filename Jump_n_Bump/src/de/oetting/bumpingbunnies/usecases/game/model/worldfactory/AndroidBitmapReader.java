package de.oetting.bumpingbunnies.usecases.game.model.worldfactory;

import java.lang.reflect.Field;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.core.worldCreation.BitmapReader;
import de.oetting.bumpingbunnies.usecases.game.graphics.AndroidBitmap;
import de.oetting.bumpingbunnies.usecases.game.model.Image;

public class AndroidBitmapReader implements BitmapReader {

	private final Resources resources;

	public AndroidBitmapReader(Resources resources) {
		super();
		this.resources = resources;
	}

	@Override
	public Image readBitmap(String filename) {
		Image bitmap = new AndroidBitmap(BitmapFactory.decodeResource(this.resources, getResourceId(filename)));
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
