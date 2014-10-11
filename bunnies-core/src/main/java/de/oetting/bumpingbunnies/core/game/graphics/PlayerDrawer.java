package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.List;

import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Player;

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
		synchronized (player) {
			if (!this.player.isDead()) {
				this.paint.setAlpha(ALPHA_WHILE_ALIVE);
			} else {
				this.paint.setAlpha(ALPHA_WHILE_DEAD);
			}
			drawAnimation(canvas);
		}
	}

	private void drawAnimation(CanvasDelegate canvas) {
		// copy to avoid changes in player which might lead to a sitatuation
		// where no animation should be animated because the player is changed
		// in between
		Player copiedPlayer = player.clone();
		for (ConditionalMirroredAnimation ani : this.animations) {
			if (ani.shouldBeExecuted(copiedPlayer)) {
				ani.drawMirrored(copiedPlayer.isFacingLeft());
				ani.draw(canvas, copiedPlayer.minX(), copiedPlayer.maxY(), this.paint);
				return;
			}
		}
		LOGGER.error(this.player.toString());
		throw new IllegalStateException("cannot find animation");
	}

	@Override
	public boolean drawsPlayer(Player p) {
		return this.player.equals(p);
	}

}
