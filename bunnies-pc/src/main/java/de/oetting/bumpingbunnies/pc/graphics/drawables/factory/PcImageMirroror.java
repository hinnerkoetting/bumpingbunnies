package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import de.oetting.bumpingbunnies.core.game.graphics.ImageMirroror;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

public class PcImageMirroror implements ImageMirroror {

	@Override
	public ImageWrapper mirrorImage(ImageWrapper image) {
		Image fxImage = (Image) image.getBitmap();
		ImageView imageView = new ImageView(fxImage);
		imageView.setScaleX(-1);
		Image coloredImage = imageView.getImage();
		return new ImageWrapper(coloredImage);
	}

}
