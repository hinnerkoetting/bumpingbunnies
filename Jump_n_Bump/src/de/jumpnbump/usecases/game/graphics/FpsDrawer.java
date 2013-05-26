package de.jumpnbump.usecases.game.graphics;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import de.jumpnbump.usecases.game.GameThreadState;

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
	public void draw(Canvas canvas) {
		int fpsCount = this.state.getLastFpsCount();
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		canvas.drawText(String.format("FPS: %d", fpsCount),
				(int) (this.x * width), (int) (this.y * height), this.paint);

	}
}
