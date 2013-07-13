package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerDrawer implements Drawable {

	private final Player player;
	private final AnimationWithMirror runningAnimation;
	private final Paint paint;

	public PlayerDrawer(Player player, AnimationWithMirror runningAnimation) {
		this.player = player;
		this.runningAnimation = runningAnimation;
		this.paint = new Paint();
		this.paint.setAlpha(125);
		this.paint.setColor(this.player.getColor());
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		if (!this.player.isDead()) {
			if (this.player.movementX() != 0) {
				this.runningAnimation.drawMirrored(this.player.isFacingLeft());
			}
			// canvas.drawRect(this.player.minX(), this.player.maxY(),
			// this.player.maxX(), this.player.minY(), this.paint);
			this.runningAnimation.draw(canvas, this.player.minX(),
					this.player.maxY(), this.paint);
		}
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas) {
		int width = (int) (canvas.transformX(this.player.maxX()) - canvas
				.transformX(this.player.minX()));
		int height = (int) (canvas.transformX(this.player.maxY()) - canvas
				.transformX(this.player.minY()));
		this.runningAnimation.updateGraphics(canvas, width, height);
	}

}
