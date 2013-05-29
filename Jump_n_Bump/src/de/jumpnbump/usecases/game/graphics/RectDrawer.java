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
		canvas.drawRect(this.object.minX(), this.object.minY(),
				this.object.maxX(), this.object.maxY(), this.paint);
	}
}