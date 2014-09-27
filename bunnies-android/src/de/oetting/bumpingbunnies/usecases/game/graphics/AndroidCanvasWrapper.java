package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.graphics.Canvas;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;

public class AndroidCanvasWrapper extends CanvasWrapper {

	public AndroidCanvasWrapper(Canvas canvasImpl) {
		super(canvasImpl);
	}

	@Override
	public Canvas getCanvasImpl() {
		return (Canvas) super.getCanvasImpl();
	}

	@Override
	public int getCanvasWidth() {
		return getCanvasImpl().getWidth();
	}

	@Override
	public int getCanvasHeight() {
		return getCanvasImpl().getHeight();
	}

}
