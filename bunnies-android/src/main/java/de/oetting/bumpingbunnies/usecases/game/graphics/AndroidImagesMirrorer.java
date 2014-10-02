package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import de.oetting.bumpingbunnies.core.game.graphics.ImageMirroror;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

public class AndroidImagesMirrorer implements ImageMirroror {

	@Override
	public ImageWrapper mirrorImage(ImageWrapper wrapper) {
		Bitmap bitmap = (Bitmap) wrapper.getBitmap();
		Matrix mirror = new Matrix();
		mirror.preScale(-1.0f, 1.0f);
		Bitmap mirroredBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), mirror, false);
		return new ImageWrapper(mirroredBitmap);
	}

}
