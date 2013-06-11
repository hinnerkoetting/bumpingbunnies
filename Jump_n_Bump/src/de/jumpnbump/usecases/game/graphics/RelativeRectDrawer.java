package de.jumpnbump.usecases.game.graphics;

import android.graphics.Paint;

public class RelativeRectDrawer implements Drawable {

	private Paint paint;
	private final double minX;
	private final double minY;
	private final double maxX;
	private final double maxY;

	public RelativeRectDrawer(double minX, double minY, double maxX,
			double maxY, int color) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.paint = new Paint();
		this.paint.setColor(color);
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		canvas.drawRectRelativeToScreen(this.minX, this.minY, this.maxX,
				this.maxY, this.paint);
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas) {

	}

}
