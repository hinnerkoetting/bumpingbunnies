package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.ImagesColorer;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.pc.graphics.PaintConverter;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

public class PcImagesColoror implements ImagesColorer {

	@Override
	public ImageWrapper colorImage(ImageWrapper image, int color) {
		Image fxImage = (Image) image.getBitmap();
		ColorAdjust colorAdjust = createColorAdjust(color);
		ImageView imageView = new ImageView(fxImage);
		imageView.setEffect(colorAdjust);
		return new ImageFromViewExtractor().extractToWrapper(imageView);
	}

	private ColorAdjust createColorAdjust(int color) {
		Paint paint = new Paint(color);
		Color convert = (Color) new PaintConverter().convert(paint);
		ColorAdjust colorAdjust = new ColorAdjust();
		colorAdjust.setHue(convert.getHue());
		colorAdjust.setSaturation(0.5);
		return colorAdjust;
	}
}
