package de.oetting.bumpingbunnies.usecases.game.graphics;

import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class ScoreDrawer implements Drawable {

	private final Player player;
	private final Paint paint;
	private double scoreX;
	private double scoreY;

	public ScoreDrawer(Player player, double scoreX, double scoreY) {
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
		int score = this.player.getScore();
		canvas.drawTextRelativeToScreen(Integer.toString(score), this.scoreX, this.scoreY, this.paint);
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas) {
	}

	@Override
	public boolean drawsPlayer(Player p) {
		return false;
	}
}
