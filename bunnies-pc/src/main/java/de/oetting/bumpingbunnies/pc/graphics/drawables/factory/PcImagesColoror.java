package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.ImagesColorer;
import de.oetting.bumpingbunnies.core.graphics.ImageColoror;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class PcImagesColoror implements ImagesColorer {

	@Override
	public ImageWrapper colorImage(ImageWrapper image, int color) {
		Image fxImage = (Image) image.getBitmap();
		WritableImage writableImage = createColoredImage(color, fxImage);
		return new ImageFromViewExtractor().extractToWrapper(new ImageView(writableImage));
	}

	private WritableImage createColoredImage(int color, Image fxImage) {
		PixelReader pixelReader = fxImage.getPixelReader();
		WritableImage writableImage = new WritableImage((int) fxImage.getWidth(), (int) fxImage.getHeight());
		PixelWriter pixelWriter = writableImage.getPixelWriter();
		colorAllPixels(color, fxImage, pixelReader, pixelWriter);
		return writableImage;
	}

	private void colorAllPixels(int color, Image fxImage, PixelReader pixelReader, PixelWriter pixelWriter) {
		for (int row = 0; row < fxImage.getHeight(); row++) {
			for (int column = 0; column < fxImage.getWidth(); column++) {
				int origColor = pixelReader.getArgb(column, row);
				int targetColor = ImageColoror.colorPixel(origColor, color);
				pixelWriter.setArgb(column, row, targetColor);
			}
		}
	}
}
