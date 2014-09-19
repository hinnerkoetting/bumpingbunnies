package de.oetting.bumpingbunnies.usecases.game.graphics;

import de.oetting.bumpingbunnies.usecases.game.model.Image;
import android.graphics.Bitmap;

public class AndroidBitmap implements Image {

	private final Bitmap bitmap;

	public AndroidBitmap(Bitmap bitmap) {
		super();
		this.bitmap = bitmap;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

}
