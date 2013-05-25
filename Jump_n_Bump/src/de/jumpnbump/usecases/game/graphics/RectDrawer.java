package de.jumpnbump.usecases.game.graphics;

import android.graphics.Canvas;
import de.jumpnbump.usecases.game.model.GameObject;

public class RectDrawer implements Drawable {

	private final GameObject object;

	public RectDrawer(GameObject object) {
		this.object = object;
	}

	@Override
	public void draw(Canvas canvas) {
		int minX = (int) (this.object.minX() * canvas.getWidth());
		int maxX = (int) (this.object.maxX() * canvas.getWidth());
		int minY = (int) (this.object.minY() * canvas.getHeight());
		int maxY = (int) (this.object.maxY() * canvas.getHeight());
		canvas.drawRect(minX, minY, maxX, maxY, this.object.getColor());
	}
}