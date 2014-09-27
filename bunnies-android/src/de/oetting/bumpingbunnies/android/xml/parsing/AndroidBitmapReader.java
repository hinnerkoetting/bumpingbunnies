package de.oetting.bumpingbunnies.android.xml.parsing;

import java.lang.reflect.Field;

import android.content.res.Resources;
import android.graphics.BitmapFactory;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.core.worldCreation.BitmapReader;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

public class AndroidBitmapReader implements BitmapReader {

	private final Resources resources;

	public AndroidBitmapReader(Resources resources) {
		super();
		this.resources = resources;
	}

	@Override
	public ImageWrapper readBitmap(String filename) {
		return new ImageWrapper(BitmapFactory.decodeResource(this.resources, getResourceId(filename)));
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
