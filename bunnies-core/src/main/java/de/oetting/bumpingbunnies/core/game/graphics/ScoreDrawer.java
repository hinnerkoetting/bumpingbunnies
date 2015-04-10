package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.model.color.Color;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class ScoreDrawer implements Drawable {

	private final Bunny player;
	private final Paint paint;
	private final Paint backgroundPaint;
	private double scoreXPosition;
	private double scoreYPosition;

	public ScoreDrawer(Bunny player, double scoreX, double scoreY) {
		this.player = player;
		this.scoreXPosition = scoreX;
		this.scoreYPosition = scoreY;
		this.paint = createScorePaint(player);
		this.backgroundPaint = createBackgroundOfScorePaint();
	}

	private Paint createScorePaint(Bunny player) {
		Paint paint = new Paint();
		paint.setColor(player.getColor());
		paint.setTextSize(20);
		return paint;
	}
	
	private Paint createBackgroundOfScorePaint() {
		Paint backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.TRANS_LT_BLUE);
		return backgroundPaint;
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		int score = this.player.getScore();
		
		int leftAbsolute = (int) ((scoreXPosition) * canvas.getOriginalWidth() - 5);
		int topAbsolute = (int) ((scoreYPosition) * canvas.getOriginalHeight() - paint.getTextSize());
		String text = Integer.toString(score);
		int height = canvas.getTextHeight(text, paint);
		int width = canvas.getTextWidth(text, paint);
		canvas.drawRectAbsoluteScreen(leftAbsolute, topAbsolute, leftAbsolute + width + 7, topAbsolute + height + 5, backgroundPaint);
		canvas.drawTextRelativeToScreen(text, this.scoreXPosition, this.scoreYPosition, this.paint);
	}

	@Override
	public boolean drawsPlayer(Bunny p) {
		return player.equals(p);
	}
}
