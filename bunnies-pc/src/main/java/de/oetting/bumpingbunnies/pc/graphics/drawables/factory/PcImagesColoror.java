package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.ImagesColorer;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

public class PcImagesColoror implements ImagesColorer {

	@Override
	public ImageWrapper colorImage(ImageWrapper image, int color) {
		Image fxImage = (Image) image.getBitmap();
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setContrast(0.1);
		colorAdjust.setHue(-0.05);
		colorAdjust.setBrightness(0.1);
		colorAdjust.setSaturation(0.2);
		ImageView imageView = new ImageView(fxImage);
		imageView.setEffect(colorAdjust);
		Image coloredImage = imageView.getImage();
		return new ImageWrapper(coloredImage);
	}

}
