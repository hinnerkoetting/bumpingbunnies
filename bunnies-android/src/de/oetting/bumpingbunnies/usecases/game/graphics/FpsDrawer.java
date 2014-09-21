package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.graphics.Color;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

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
		this.paint.setColor(Color.BLACK);
		this.paint.setTextSize(30);
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		int fpsCount = this.state.getLastFpsCount();
		canvas.drawTextRelativeToScreen(String.format("FPS: %d", fpsCount), this.x, this.y, this.paint);

	}

	@Override
	public void updateGraphics(CanvasDelegate canvas) {
	}

	@Override
	public boolean drawsPlayer(Player p) {
		return false;
	}
}
