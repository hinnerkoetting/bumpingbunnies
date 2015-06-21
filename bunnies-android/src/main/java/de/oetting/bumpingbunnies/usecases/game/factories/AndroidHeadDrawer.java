package de.oetting.bumpingbunnies.usecases.game.factories;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import de.oetting.bumpingbunnies.android.game.graphics.SimpleBitmapResizer;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.HeadDrawer;
import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.model.game.objects.BunnyImageModel;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class AndroidHeadDrawer implements HeadDrawer {

	private final ImageResizer resizer = new SimpleBitmapResizer();
	
	@Override
	public ImageWrapper overDrawFace(ImageWrapper originalImage, ImageWrapper drawOverImage, BunnyImageModel model) {
		Bitmap originalAndroidImage = (Bitmap) originalImage.getBitmap();
		ImageWrapper resizedDrawOverImage = resizer.resize(drawOverImage, (int) (2.0 * model.getRadius() / model.getWidth()
				* originalAndroidImage.getWidth()),
				(int) (2.0 * model.getRadius() / model.getHeight() * originalAndroidImage.getHeight()));
		Bitmap drawOverFxImage = (Bitmap) resizedDrawOverImage.getBitmap();
		Bitmap bitmap = Bitmap.createBitmap(originalAndroidImage.getWidth(), originalAndroidImage.getHeight(), Config.ARGB_8888);
		Canvas bitmapCanvas = new Canvas(bitmap);
		bitmapCanvas.drawBitmap(originalAndroidImage, 0, 0, new Paint());
		drawOverFace(originalImage, model, drawOverFxImage, bitmapCanvas);
		return new ImageWrapper(bitmap, originalImage.getImageKey());
	}

	private void drawOverFace(ImageWrapper originalImage, BunnyImageModel model, Bitmap drawOverFxImage, Canvas canvas) {
		double relativeStartXPosition = model.getHeadCenterX() - model.getRadius();
		int absoluteStartXPosition = (int) (relativeStartXPosition / model.getWidth() * canvas.getWidth());
		double relativeStartYPosition = model.getHeadCenterY() - model.getRadius();
		int absoluteStartYPosition = (int) (relativeStartYPosition / model.getHeight() * canvas.getHeight());
		canvas.drawBitmap(drawOverFxImage, absoluteStartXPosition, absoluteStartYPosition, new Paint());
	}

}
