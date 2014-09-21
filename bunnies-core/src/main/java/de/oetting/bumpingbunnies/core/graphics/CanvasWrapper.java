package de.oetting.bumpingbunnies.core.graphics;

public class CanvasWrapper {

	private final Object canvasImpl;

	public CanvasWrapper(Object canvasImpl) {
		this.canvasImpl = canvasImpl;
	}

	public Object getCanvasImpl() {
		return canvasImpl;
	}

}
