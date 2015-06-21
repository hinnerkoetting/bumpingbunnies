package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.HeadDrawer;
import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.model.game.objects.BunnyImageModel;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class PcHeadImageDrawer implements HeadDrawer {

	private final ImageResizer resizer = new PcImagesResizer();

	@Override
	public ImageWrapper overDrawFace(ImageWrapper originalImage, ImageWrapper drawOverImage, BunnyImageModel model) {
		Image originalFxImage = (Image) originalImage.getBitmap();
		ImageWrapper resizedDrawOverImage = resizer.resize(drawOverImage, (int) (2.0 * model.getRadius() / model.getWidth()
				* originalFxImage.getWidth()),
				(int) (2.0 * model.getRadius() / model.getHeight() * originalFxImage.getHeight()));
		Image drawOverFxImage = (Image) resizedDrawOverImage.getBitmap();
		Canvas fxCanvas = new Canvas(originalFxImage.getWidth(), originalFxImage.getHeight());
		GraphicsContext context2d = fxCanvas.getGraphicsContext2D();
		context2d.drawImage(originalFxImage, 0, 0);
		drawOverFace(originalImage, model, drawOverFxImage, fxCanvas);
		Image resultImage = new ImageFromViewExtractor().takeSnapshot(fxCanvas);
		return new ImageWrapper(resultImage, originalImage.getImageKey());
	}

	private void drawOverFace(ImageWrapper originalImage, BunnyImageModel model, Image drawOverFxImage, Canvas fxCanvas) {
		GraphicsContext context2d = fxCanvas.getGraphicsContext2D();
		double relativeStartXPosition = model.getHeadCenterX() - model.getRadius();
		int absoluteStartXPosition = (int) (relativeStartXPosition / model.getWidth() * fxCanvas.getWidth());
		double relativeStartYPosition = model.getHeadCenterY() - model.getRadius();
		int absoluteStartYPosition = (int) (relativeStartYPosition / model.getHeight() * fxCanvas.getHeight());
		context2d.drawImage(drawOverFxImage, absoluteStartXPosition, absoluteStartYPosition);
	}

}
