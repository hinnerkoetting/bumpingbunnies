package de.jumpnbump.usecases.game.graphics;

import android.graphics.Paint;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.PlayerState;

public class ScoreDrawer implements Drawable {

	private Player player;
	private int scoreX;
	private int scoreY;
	private Paint paint;

	public ScoreDrawer(Player player, int scoreX, int scoreY) {
		super();
		this.player = player;
		this.scoreX = scoreX;
		this.scoreY = scoreY;
		this.paint = new Paint();
		this.paint.setColor(player.getColor());
		this.paint.setTextSize(50);
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		PlayerState state = this.player.getState();
		canvas.drawText(Integer.toString(state.getScore()), this.scoreX,
				this.scoreY, this.paint);
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas) {
	}
}
