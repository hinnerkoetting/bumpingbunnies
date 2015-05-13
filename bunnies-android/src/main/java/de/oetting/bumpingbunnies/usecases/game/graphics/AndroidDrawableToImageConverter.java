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
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class AndroidDrawableToImageConverter implements DrawableToImageConverter {

	private final CanvasAdapter screenCanvas;
	private final CoordinatesCalculation coordinatesCalculation;
	private final Context context;

	public AndroidDrawableToImageConverter(CanvasAdapter canvas, CoordinatesCalculation coordinatesCalculation, Context context) {
		this.screenCanvas = canvas;
		this.coordinatesCalculation = coordinatesCalculation;
		this.context = context;
	}

	@Override
	public ImageWrapper drawOnImage(List<Drawable> drawable) {
		Bitmap bitmap = Bitmap.createBitmap(screenCanvas.getOriginalWidth(), screenCanvas.getOriginalHeight(), Config.ARGB_8888);
		Canvas androidCanvas = new Canvas(bitmap);
		CanvasAdapter delegate = createObjectToDrawOnCanvas(androidCanvas);
		drawOnTempCanvas(delegate, drawable);
		return new ImageWrapper(bitmap, "all");
	}

	private void drawOnTempCanvas(CanvasAdapter delegate, List<Drawable> drawable) {
		for (Drawable d : drawable) {
			d.draw(delegate);
		}
	}

	private CanvasAdapter createObjectToDrawOnCanvas(Canvas fxCanvas) {
		AndroidCanvasAdapter toTempCanvasDelegate = new AndroidCanvasAdapter(context);
		CanvasWrapper wrapper = new AndroidCanvasWrapper(fxCanvas);
		toTempCanvasDelegate.updateDelegate(wrapper);
		return new CanvasCoordinateTranslator(toTempCanvasDelegate, coordinatesCalculation);
	}

}