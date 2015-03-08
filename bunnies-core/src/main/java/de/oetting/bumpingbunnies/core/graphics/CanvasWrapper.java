package de.oetting.bumpingbunnies.core.graphics;

public abstract class CanvasWrapper {

	private final Object canvasImpl;

	public CanvasWrapper(Object canvasImpl) {
		this.canvasImpl = canvasImpl;
	}

	public Object getCanvasImpl() {
		return canvasImpl;
	}

	public abstract int getCanvasWidth();

	public abstract int getCanvasHeight();


}
