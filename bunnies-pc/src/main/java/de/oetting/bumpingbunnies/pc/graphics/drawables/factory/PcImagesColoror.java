package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.ImagesColorer;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.pc.graphics.PaintConverter;

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
		colorAdjust.setHue((convert.getHue()) / 180.0);
		colorAdjust.setSaturation(convert.getSaturation() * 2);
		colorAdjust.setContrast(colorAdjust.getContrast());
		return colorAdjust;
	}
}
