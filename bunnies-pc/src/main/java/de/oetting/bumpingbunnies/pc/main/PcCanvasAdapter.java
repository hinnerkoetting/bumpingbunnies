package de.oetting.bumpingbunnies.pc.main;

import java.util.List;

import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasCoordinateTranslator;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasAdapter;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.pc.graphics.PcCanvasDelegate;
import de.oetting.bumpingbunnies.pc.graphics.PcCanvasWrapper;

public class PcCanvasAdapter implements CanvasAdapter {

	private final CanvasDelegate screenCanvas;
	private final CoordinatesCalculation coordinatesCalculation;

	public PcCanvasAdapter(CanvasDelegate canvas, CoordinatesCalculation coordinatesCalculation) {
		this.screenCanvas = canvas;
		this.coordinatesCalculation = coordinatesCalculation;
	}

	@Override
	public ImageWrapper drawOnImage(List<Drawable> drawable) {
		Canvas fxCanvas = new Canvas(screenCanvas.getOriginalWidth(), screenCanvas.getOriginalHeight());
		CanvasDelegate delegate = createObjectToDrawOnCanvas(fxCanvas);
		drawOnTempCanvas(delegate, drawable);
		return new ImageWrapper(takeSnapshot(fxCanvas), "all");
	}

	private void drawOnTempCanvas(CanvasDelegate delegate, List<Drawable> drawable) {
		for (Drawable d : drawable) {
			d.draw(delegate);
		}
	}

	private CanvasDelegate createObjectToDrawOnCanvas(Canvas fxCanvas) {
		PcCanvasDelegate toTempCanvasDelegate = new PcCanvasDelegate();
		CanvasWrapper wrapper = new PcCanvasWrapper(fxCanvas);
		toTempCanvasDelegate.updateDelegate(wrapper);
		return new CanvasCoordinateTranslator(toTempCanvasDelegate, coordinatesCalculation);
	}

	private Image takeSnapshot(Canvas fxCanvas) {
		SnapshotParameters params = new SnapshotParameters();
		params.setFill(Color.TRANSPARENT);
		WritableImage writableImage = new WritableImage(screenCanvas.getOriginalWidth(),
				screenCanvas.getOriginalHeight());
		fxCanvas.snapshot(params, writableImage);
		return writableImage;
	}

}
