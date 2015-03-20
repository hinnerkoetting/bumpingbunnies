package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.model.color.Color;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class ScoreDrawer implements Drawable {

	private final Player player;
	private final Paint paint;
	private final Paint backgroundPaint;
	private double scoreX;
	private double scoreY;

	public ScoreDrawer(Player player, double scoreX, double scoreY) {
		this.player = player;
		this.scoreX = scoreX;
		this.scoreY = scoreY;
		this.paint = new Paint();
		this.paint.setColor(player.getColor());
		this.paint.setTextSize(20);
		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.TRANS_LTGRAY);
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		int score = this.player.getScore();
		
		int leftAbsolute = (int) ((scoreX) * canvas.getOriginalWidth() - 10);
		int topAbsolute = (int) ((scoreY) * canvas.getOriginalHeight() - 15);
		canvas.drawRectAbsoluteScreen(leftAbsolute, topAbsolute, leftAbsolute + 25, topAbsolute + 20, backgroundPaint);
		canvas.drawTextRelativeToScreen(Integer.toString(score), this.scoreX, this.scoreY, this.paint);
	}

	@Override
	public boolean drawsPlayer(Player p) {
		return player.equals(p);
	}
}
