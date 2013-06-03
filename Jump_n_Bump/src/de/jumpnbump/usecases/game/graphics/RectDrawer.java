package de.jumpnbump.usecases.game.graphics;

import android.graphics.Paint;
import de.jumpnbump.usecases.game.model.GameObject;

public class RectDrawer implements Drawable {

	private final GameObject object;
	private Paint paint;

	public RectDrawer(GameObject object) {
		this.object = object;
		this.paint = new Paint();
		this.paint.setColor(object.getColor());
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		this.paint.setColor(this.object.getColor());
		canvas.drawRect(this.object.minX(), this.object.maxY(),
				this.object.maxX(), this.object.minY(), this.paint);
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas) {
	}
}