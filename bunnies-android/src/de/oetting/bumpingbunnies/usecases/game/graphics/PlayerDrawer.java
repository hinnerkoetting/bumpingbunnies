package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.List;

import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerDrawer implements Drawable {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerDrawer.class);
	private static final int ALPHA_WHILE_ALIVE = 255;
	private static final int ALPHA_WHILE_DEAD = 64;
	private final Player player;
	private final Paint paint;
	private List<ConditionalMirroredAnimation> animations;

	public PlayerDrawer(Player player, List<ConditionalMirroredAnimation> animations) {
		this.player = player;
		this.animations = animations;
		this.paint = new Paint();
		this.paint.setColor(this.player.getColor());
	}

	@Override
	public void draw(CanvasDelegate canvas) {
		if (!this.player.isDead()) {
			this.paint.setAlpha(ALPHA_WHILE_ALIVE);
		} else {
			this.paint.setAlpha(ALPHA_WHILE_DEAD);
		}
		drawAnimation(canvas);
	}

	private void drawAnimation(CanvasDelegate canvas) {
		for (ConditionalMirroredAnimation ani : this.animations) {
			if (ani.shouldBeExecuted()) {
				ani.drawMirrored(this.player.isFacingLeft());
				ani.draw(canvas, this.player.minX(), this.player.maxY(), this.paint);
				return;
			}
		}
		LOGGER.error(this.player.toString());
		throw new IllegalStateException("cannot find animation");
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas) {
		int width = (int) (canvas.transformX(this.player.maxX()) - canvas.transformX(this.player.minX()));
		int height = (int) (canvas.transformX(this.player.maxY()) - canvas.transformX(this.player.minY()));
		for (ConditionalAnimation ani : this.animations) {
			ani.updateGraphics(canvas, width, height);
		}
	}

	@Override
	public boolean drawsPlayer(Player p) {
		return this.player.equals(p);
	}

}
