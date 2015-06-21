package de.oetting.bumpingbunnies.pc.main;

import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasAdapter;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasCoordinateTranslator;
import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.DrawableToImageConverter;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.pc.graphics.PcCanvasAdapter;
import de.oetting.bumpingbunnies.pc.graphics.PcCanvasWrapper;
import de.oetting.bumpingbunnies.pc.graphics.drawables.factory.ImageFromViewExtractor;

public class PcDrawableToImageConverter implements DrawableToImageConverter {

	private final CanvasAdapter screenCanvas;
	private final CoordinatesCalculation coordinatesCalculation;

	public PcDrawableToImageConverter(CanvasAdapter canvas, CoordinatesCalculation coordinatesCalculation) {
		this.screenCanvas = canvas;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public ImageWrapper drawOnImage(List<Drawable> drawable) {
		Canvas fxCanvas = new Canvas(screenCanvas.getOriginalWidth(), screenCanvas.getOriginalHeight());
		CanvasAdapter delegate = createObjectToDrawOnCanvas(fxCanvas);
		drawOnTempCanvas(delegate, drawable);
		return new ImageWrapper(takeSnapshot(fxCanvas), "all");
	}

	private void drawOnTempCanvas(CanvasAdapter delegate, List<Drawable> drawable) {
		for (Drawable d : drawable) {
			d.draw(delegate);
		}
	}

	private CanvasAdapter createObjectToDrawOnCanvas(Canvas fxCanvas) {
		PcCanvasAdapter toTempCanvasDelegate = new PcCanvasAdapter();
		CanvasWrapper wrapper = new PcCanvasWrapper(fxCanvas);
		toTempCanvasDelegate.updateDelegate(wrapper);
		return new CanvasCoordinateTranslator(toTempCanvasDelegate, coordinatesCalculation);
	}

	private Image takeSnapshot(Canvas fxCanvas) {
		return new ImageFromViewExtractor().takeSnapshot(fxCanvas);
	}

}
