package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import de.oetting.bumpingbunnies.core.game.graphics.DrawableToImageConverter;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasCoordinateTranslator;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasAdapter;
import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.AbsoluteCoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;

public class AndroidDrawableToImageConverter implements DrawableToImageConverter {

	private final CanvasAdapter screenCanvas;
	private final CoordinatesCalculation coordinatesCalculation;
	private final Context context;

	public AndroidDrawableToImageConverter(CanvasAdapter canvas, CoordinatesCalculation coordinatesCalculation,
			Context context) {
		this.screenCanvas = canvas;
		this.coordinatesCalculation = coordinatesCalculation;
		this.context = context;
	}

	@Override
	public ImageWrapper drawOnImage(List<Drawable> drawable) {
		int bitmapWidth = coordinatesCalculation.getScreenCoordinateX(ModelConstants.STANDARD_WORLD_SIZE)
				- coordinatesCalculation.getScreenCoordinateX(0);
		int bitmapHeight = coordinatesCalculation.getScreenCoordinateY(0)
				- coordinatesCalculation.getScreenCoordinateY(ModelConstants.STANDARD_WORLD_SIZE);
		Bitmap bitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight, Config.ARGB_4444);
		Canvas bitmapCanvas = new Canvas(bitmap);
		CanvasAdapter delegate = createObjectToDrawOnCanvas(bitmapCanvas);
		drawOnTempCanvas(delegate, drawable);
		return new ImageWrapper(bitmap, "all");
	}

	private void drawOnTempCanvas(CanvasAdapter delegate, List<Drawable> drawable) {
		for (Drawable d : drawable) {
			d.draw(delegate);
		}
	}

	private CanvasAdapter createObjectToDrawOnCanvas(Canvas bitmapCanvas) {
		AndroidCanvasAdapter toTempCanvasDelegate = new AndroidCanvasAdapter(context);
		CanvasWrapper wrapper = new AndroidCanvasWrapper(bitmapCanvas);
		toTempCanvasDelegate.updateDelegate(wrapper);
		return new CanvasCoordinateTranslator(toTempCanvasDelegate, new AbsoluteCoordinatesCalculation(
				bitmapCanvas.getWidth(), bitmapCanvas.getHeight(), coordinatesCalculation.getWorldProperties()));
	}

}
