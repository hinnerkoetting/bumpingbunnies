package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.Player;

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
		canvas.drawRect(this.object.minX(), this.object.maxY(), this.object.maxX(), this.object.minY(), this.paint);
	}

	@Override
	public boolean drawsPlayer(Player p) {
		return false;
	}
}