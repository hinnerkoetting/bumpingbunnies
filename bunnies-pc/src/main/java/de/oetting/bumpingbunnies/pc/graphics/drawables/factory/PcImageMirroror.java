package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import de.oetting.bumpingbunnies.core.game.graphics.ImageMirroror;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class PcImageMirroror implements ImageMirroror {

	@Override
	public ImageWrapper mirrorImage(ImageWrapper image) {
		Image fxImage = (Image) image.getBitmap();
		ImageView imageView = new ImageView(fxImage);
		imageView.setScaleX(-1);
		return new ImageFromViewExtractor().extractToWrapper(imageView);
	}

}
