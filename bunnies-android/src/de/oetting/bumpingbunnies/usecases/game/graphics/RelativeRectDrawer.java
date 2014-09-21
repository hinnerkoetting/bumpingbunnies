package de.oetting.bumpingbunnies.usecases.game.graphics;

import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class RelativeRectDrawer implements Drawable {

	private Paint paint;
	private final double minX;
	private final double minY;
	private final double maxX;
	private final double maxY;

	public RelativeRectDrawer(double minX, double minY, double maxX, double maxY, int color) {
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
		this.paint = new Paint();
		this.paint.setColor(color);
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		canvas.drawRectRelativeToScreen(this.minX, this.minY, this.maxX, this.maxY, this.paint);
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas) {
	}

	@Override
	public boolean drawsPlayer(Player p) {
		return false;
	}

}
