package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.model.game.objects.Player;

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
	public boolean drawsPlayer(Player p) {
		return false;
	}
}
