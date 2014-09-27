package de.oetting.bumpingbunnies.pc.graphics;

import javafx.scene.canvas.Canvas;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;

public class PcCanvasWrapper extends CanvasWrapper {

	public PcCanvasWrapper(Canvas canvasImpl) {
		super(canvasImpl);
	}

	@Override
	public Canvas getCanvasImpl() {
		return (Canvas) super.getCanvasImpl();
	}

	@Override
	public int getCanvasWidth() {
		return (int) getCanvasImpl().getWidth();
	}

	@Override
	public int getCanvasHeight() {
		return (int) getCanvasImpl().getHeight();
	}
}
