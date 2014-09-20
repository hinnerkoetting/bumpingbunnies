package de.oetting.bumpingbunnies.usecases.game.graphics;

import de.oetting.bumpingbunnies.usecases.game.model.Image;
import android.graphics.Bitmap;

public class AndroidImage implements Image {

	private final Bitmap bitmap;

	public AndroidImage(Bitmap bitmap) {
		super();
		this.bitmap = bitmap;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

}
