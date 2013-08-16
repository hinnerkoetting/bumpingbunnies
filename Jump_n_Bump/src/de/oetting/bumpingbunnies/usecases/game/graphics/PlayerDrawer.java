package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.List;

import android.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerDrawer implements Drawable {

	private static final int ALPHA_WHILE_ALIVE = 255;
	private static final int ALPHA_WHILE_DEAD = 64;
	private final Player player;
	// private final AnimationWithMirror runningAnimation;
	private final Paint paint;
	// private AnimationWithMirror fallingAnimation;
	// private AnimationWithMirror jumpingAnimation;
	// private AnimationWithMirror sittingAnimation;
	private List<ConditionalMirroredAnimation> animations;

	public PlayerDrawer(Player player, List<ConditionalMirroredAnimation> animations) {
		this.player = player;
		// this.runningAnimation = runningAnimation;
		// this.fallingAnimation = fallingAnimation;
		// this.jumpingAnimation = jumpingAnimation;
		// this.sittingAnimation = sittingAnimation;
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
				ani.draw(canvas, this.player.minX(),
						this.player.maxY(), this.paint);
				return;
			}
		}
		throw new IllegalStateException("cannot find animation");
		// animation.drawMirrored(this.player.isFacingLeft());
		// animation.draw(canvas, this.player.minX(),
		// this.player.maxY(), this.paint);
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas) {
		int width = (int) (canvas.transformX(this.player.maxX()) - canvas
				.transformX(this.player.minX()));
		int height = (int) (canvas.transformX(this.player.maxY()) - canvas
				.transformX(this.player.minY()));
		// this.runningAnimation.updateGraphics(canvas, width, height);
		// this.fallingAnimation.updateGraphics(canvas, width, height);
		// this.jumpingAnimation.updateGraphics(canvas, width, height);
		// this.sittingAnimation.updateGraphics(canvas, width, height);
		for (ConditionalAnimation ani : this.animations) {
			ani.updateGraphics(canvas, width, height);
		}
	}

}
