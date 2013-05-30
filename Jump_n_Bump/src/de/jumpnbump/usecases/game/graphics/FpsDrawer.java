package de.jumpnbump.usecases.game.graphics;

import android.graphics.Color;
import android.graphics.Paint;
import de.jumpnbump.usecases.game.model.GameThreadState;
import de.jumpnbump.usecases.game.model.ModelConstants;

public class FpsDrawer implements Drawable {

	private final GameThreadState state;
	private Paint paint;
	private int x;
	private int y;

	public FpsDrawer(GameThreadState state) {
		this.state = state;
		this.paint = new Paint();
		this.paint.setColor(Color.BLACK);
		this.paint.setTextSize(30);
		this.x = (int) (0.5 * ModelConstants.MAX_VALUE);
		this.y = (int) (0.95 * ModelConstants.MAX_VALUE);
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		int fpsCount = this.state.getLastFpsCount();
		canvas.drawText(String.format("FPS: %d", fpsCount), this.x, this.y,
				this.paint);

	}
}
