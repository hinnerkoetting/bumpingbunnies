package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class PcImagesResizer implements ImageResizer {

	@Override
	public ImageWrapper resize(ImageWrapper original, int targetWidth, int targetHeiht) {
		Image image = (Image) original.getBitmap();
		ImageView view = new ImageView(image);
		view.setScaleX(targetWidth / image.getWidth());
		view.setScaleY(targetHeiht / image.getHeight());
		return new ImageFromViewExtractor().extractToWrapper(view);
	}

}
