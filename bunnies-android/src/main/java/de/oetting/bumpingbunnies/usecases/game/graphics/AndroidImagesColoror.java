package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.graphics.Bitmap;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.ImagesColorer;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class AndroidImagesColoror implements ImagesColorer {

	@Override
	public ImageWrapper colorImage(ImageWrapper image, int color) {
		return new ImageWrapper(GrayScaleToColorConverter.convertToColor((Bitmap) image.getBitmap(), color), image.getImageKey());
	}

}
