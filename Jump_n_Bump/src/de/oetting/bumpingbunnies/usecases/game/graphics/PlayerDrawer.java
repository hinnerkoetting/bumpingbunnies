package de.oetting.bumpingbunnies.usecases.game.graphics;

import android.graphics.Paint;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerDrawer implements Drawable {

	private static final Logger LOGGER = LoggerFactory.getLogger(PlayerDrawer.class);
	private static final int ALPHA_WHILE_ALIVE = 255;
	private static final int ALPHA_WHILE_DEAD = 64;
	private final Player player;
	private final AnimationWithMirror runningAnimation;
	private final Paint paint;
	private AnimationWithMirror fallingAnimation;
	private AnimationWithMirror jumpingAnimation;
	private AnimationWithMirror sittingAnimation;

	public PlayerDrawer(Player player, AnimationWithMirror runningAnimation, AnimationWithMirror fallingAnimation,
			AnimationWithMirror jumpingAnimation, AnimationWithMirror sittingAnimation) {
		this.player = player;
		this.runningAnimation = runningAnimation;
		this.fallingAnimation = fallingAnimation;
		this.jumpingAnimation = jumpingAnimation;
		this.sittingAnimation = sittingAnimation;
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
		AnimationWithMirror animation = findAnimation();
		drawAnimation(canvas, animation);
	}

	private AnimationWithMirror findAnimation() {
		if (Math.abs(this.player.movementX()) > ModelConstants.MOVEMENT_LIMIT
				|| Math.abs(this.player.movementY()) > ModelConstants.MOVEMENT_LIMIT) {
			return findMovingAnimation();
		}
		return this.sittingAnimation;
	}

	private void drawAnimation(CanvasDelegate canvas, AnimationWithMirror animation) {
		animation.drawMirrored(this.player.isFacingLeft());
		animation.draw(canvas, this.player.minX(),
				this.player.maxY(), this.paint);
	}

	private AnimationWithMirror findMovingAnimation() {
		if (Math.abs(this.player.movementY()) < ModelConstants.MOVEMENT_LIMIT) {
			return this.runningAnimation;
		} else {
			return findVerticalMovement();
		}
	}

	private AnimationWithMirror findVerticalMovement() {
		if (this.player.movementY() > 0) {
			return this.jumpingAnimation;
		} else {
			return this.fallingAnimation;
		}
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas) {
		int width = (int) (canvas.transformX(this.player.maxX()) - canvas
				.transformX(this.player.minX()));
		int height = (int) (canvas.transformX(this.player.maxY()) - canvas
				.transformX(this.player.minY()));
		this.runningAnimation.updateGraphics(canvas, width, height);
		this.fallingAnimation.updateGraphics(canvas, width, height);
		this.jumpingAnimation.updateGraphics(canvas, width, height);
		this.sittingAnimation.updateGraphics(canvas, width, height);
	}

}
