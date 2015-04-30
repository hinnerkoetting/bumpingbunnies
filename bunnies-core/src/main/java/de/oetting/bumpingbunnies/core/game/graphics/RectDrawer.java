package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithColor;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class RectDrawer implements Drawable {

	private final GameObjectWithColor object;
	private Paint paint;

	public RectDrawer(GameObjectWithColor object) {
		this.object = object;
		this.paint = new Paint();
		this.paint.setColor(object.getColor());
	}

	@Override
	public void draw(CanvasAdapter canvas) {
		this.paint.setColor(this.object.getColor());
		canvas.drawRect(this.object.minX(), this.object.maxY(), this.object.maxX(), this.object.minY(), this.paint);
	}

	@Override
	public boolean drawsPlayer(Bunny p) {
		return false;
	}
}