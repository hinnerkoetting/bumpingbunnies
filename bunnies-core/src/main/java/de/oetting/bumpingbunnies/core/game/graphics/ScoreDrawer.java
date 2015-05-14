package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.color.Color;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class ScoreDrawer implements Drawable {

	private final Bunny player;
	private final Paint paint;
	private final Paint backgroundPaint;
	private World world;

	public ScoreDrawer(Bunny player, World world) {
		this.player = player;
		this.world = world;
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
	public void draw(CanvasAdapter canvas) {
		int score = this.player.getScore();
		double scoreXPosition = getScoreXPosition();
		double scoreYPosition = getScoreYPosition();
		int leftAbsolute = (int) ((scoreXPosition) * canvas.getOriginalWidth() - 5);
		int topAbsolute = (int) ((scoreYPosition) * canvas.getOriginalHeight() - paint.getTextSize());
		String text = Integer.toString(score);
		int height = canvas.getTextHeight(text, paint);
		int width = canvas.getTextWidth(text, paint);
		canvas.drawRectAbsoluteScreen(leftAbsolute, topAbsolute, leftAbsolute + width + 7, topAbsolute + height + 5, backgroundPaint);
		canvas.drawTextRelativeToScreen(text, scoreXPosition, scoreYPosition, this.paint);
	}

	private double getScoreYPosition() {
		int playerNumber = world.getIndexOfPlayer(player);
		return (playerNumber / 5) * 0.1 + 0.1;
	}

	private double getScoreXPosition() {
		int playerNumber = world.getIndexOfPlayer(player);
		return 0.1 + (playerNumber % 5) * 0.2;
	}

	@Override
	public boolean drawsPlayer(Bunny p) {
		return player.equals(p);
	}
}
