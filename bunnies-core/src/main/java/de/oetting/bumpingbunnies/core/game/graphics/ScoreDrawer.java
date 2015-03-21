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
		
		int leftAbsolute = (int) ((scoreX) * canvas.getOriginalWidth() - 5);
		int topAbsolute = (int) ((scoreY) * canvas.getOriginalHeight() - paint.getTextSize());
		String text = Integer.toString(score);
		int height = canvas.getTextHeight(text, paint);
		int width = canvas.getTextWidth(text, paint);
		canvas.drawRectAbsoluteScreen(leftAbsolute, topAbsolute, leftAbsolute + width + 5, topAbsolute + height + 5, backgroundPaint);
		canvas.drawTextRelativeToScreen(text, this.scoreX, this.scoreY, this.paint);
	}

	@Override
	public boolean drawsPlayer(Player p) {
		return player.equals(p);
	}
}
