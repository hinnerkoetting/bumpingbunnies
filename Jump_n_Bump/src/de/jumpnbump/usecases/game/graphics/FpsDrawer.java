package de.jumpnbump.usecases.game.graphics;

import android.graphics.Color;
import android.graphics.Paint;
import de.jumpnbump.usecases.game.model.GameThreadState;

public class FpsDrawer implements Drawable {

	private final GameThreadState state;
	private Paint paint;
	private double x;
	private double y;

	public FpsDrawer(GameThreadState state) {
		this.state = state;
		this.paint = new Paint();
		this.paint.setColor(Color.BLACK);
		this.paint.setTextSize(30);
		this.x = 0.05;
		this.y = 0.05;
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		int fpsCount = this.state.getLastFpsCount();
		canvas.drawText(String.format("FPS: %d", fpsCount), this.x, this.y,
				this.paint);

	}
}
