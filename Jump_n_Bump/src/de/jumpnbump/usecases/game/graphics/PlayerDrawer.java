package de.jumpnbump.usecases.game.graphics;

import android.graphics.Paint;
import de.jumpnbump.usecases.game.model.Player;

public class PlayerDrawer implements Drawable {

	private final Player player;
	private AnimationWithMirror runningAnimation;
	private Paint paint;

	public PlayerDrawer(Player player, AnimationWithMirror runningAnimation) {
		this.player = player;
		this.runningAnimation = runningAnimation;
		this.paint = new Paint();
		this.paint.setAlpha(125);
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		this.paint.setColor(this.player.getColor());
		if (this.player.movementX() != 0) {
			this.runningAnimation.drawMirrored(this.player.movementX() < 0);
		}
		canvas.drawRect(this.player.minX(), this.player.maxY(),
				this.player.maxX(), this.player.minY(), this.paint);
		this.runningAnimation.draw(canvas, this.player.minX(),
				this.player.maxY(), this.paint);
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas) {
		int width = (int) (canvas.transformX(this.player.maxX()) - canvas
				.transformX(this.player.minX()));
		int height = (int) (canvas.transformX(this.player.maxX()) - canvas
				.transformX(this.player.minX()));
		this.runningAnimation.updateGraphics(canvas, width, height);
	}

}
