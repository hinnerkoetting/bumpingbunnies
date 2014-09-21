package de.oetting.bumpingbunnies.usecases.game.graphics;

import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

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
	public void updateGraphics(CanvasDelegate canvas) {
	}

	@Override
	public boolean drawsPlayer(Player p) {
		return false;
	}
}