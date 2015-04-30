package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class FpsDrawer implements Drawable {

	private final GameThreadState state;
	private Paint paint;
	private double x;
	private double y;

	public FpsDrawer(GameThreadState state) {
		this.state = state;
		this.x = 0.05;
		this.y = 0.05;
		this.paint = new Paint();
		this.paint.setColor(Paint.LIGHT_GRAY);
		this.paint.setTextSize(10);
	}

	@Override
	public void draw(CanvasAdapter canvas) {
		int fpsCount = this.state.getLastFpsCount();
		canvas.drawTextRelativeToScreen(String.format("FPS: %d", fpsCount), this.x, this.y, this.paint);

	}

	@Override
	public boolean drawsPlayer(Bunny p) {
		return false;
	}
}
