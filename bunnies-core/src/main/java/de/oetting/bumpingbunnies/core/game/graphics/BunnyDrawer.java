package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.List;

import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class BunnyDrawer implements Drawable {

	private static final Logger LOGGER = LoggerFactory.getLogger(BunnyDrawer.class);
	private static final int ALPHA_WHILE_ALIVE = 255;
	private static final int ALPHA_WHILE_IN_WATER = 96;
	private static final int ALPHA_WHILE_DEAD = 64;
	private static final int TIME_MILLIS_BLINKING_AFTER_DEAD = 1000;
	private static final int NUMBER_OF_BLINKS = 5;
	
	private final Bunny player;
	private final Paint paint;
	private List<ConditionalMirroredAnimation> animations;

	private boolean drawLighterIfDead = true;
	private long timeSinceLastLightningChange;
	private long timeSinceLastDeadTime;
	private boolean wasDeadDuringLastRendering = false;

	public BunnyDrawer(Bunny player, List<ConditionalMirroredAnimation> animations) {
		this.player = player;
		this.animations = animations;
		this.paint = new Paint();
		this.paint.setColor(this.player.getColor());
	}

	@Override
	public void draw(CanvasAdapter canvas) {
		setStatesNecessaryForBunnyBlinking();
		paint.setAlpha(computeAlpha());
		if (canvas.isVisible(player.getCenterX(), player.getCenterY()))
			drawAnimation(canvas);
		else
			drawMarkerAtBorder(canvas);
	}

	private void setStatesNecessaryForBunnyBlinking() {
		if (player.isDead() && player.getOpponent().isLocalHumanPlayer()) {
			if (!wasDeadDuringLastRendering) {
				wasDeadDuringLastRendering = true;
				timeSinceLastLightningChange = System.currentTimeMillis();
				timeSinceLastDeadTime = System.currentTimeMillis();
				drawLighterIfDead = true;
			}
		} else {
			wasDeadDuringLastRendering = false;
		}
	}

	private boolean drawLighter() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - timeSinceLastDeadTime < TIME_MILLIS_BLINKING_AFTER_DEAD) {
			if (currentTime - timeSinceLastLightningChange > TIME_MILLIS_BLINKING_AFTER_DEAD / NUMBER_OF_BLINKS) {
				drawLighterIfDead = !drawLighterIfDead;
				timeSinceLastLightningChange = currentTime;
			}
			return drawLighterIfDead;
		}
		return false;
	}

	private int computeAlpha() {
		return computeBaseAlpha() / (drawLighter() ? 10 : 1);
	}

	private int computeBaseAlpha() {
		if (!this.player.isDead()) {
			if (player.isInWater())
				return ALPHA_WHILE_IN_WATER;
			return ALPHA_WHILE_ALIVE;
		} else {
			return ALPHA_WHILE_DEAD;
		}
	}

	private void drawAnimation(CanvasAdapter canvas) {
		// copy to avoid changes in player which might lead to a situation
		// where no animation should be animated because the player is changed
		// in between condition checks.
		Bunny copiedPlayer = player.clone();
		for (ConditionalMirroredAnimation ani : this.animations) {
			if (ani.shouldBeExecuted(copiedPlayer)) {
				ani.drawMirrored(copiedPlayer.isFacingLeft());
				ani.draw(canvas, minXPosition(canvas, ani, copiedPlayer), maxYPosition(canvas, ani, copiedPlayer),
						this.paint);
				return;
			}
		}
		LOGGER.error(this.player.toString());
		throw new IllegalStateException("cannot find animation");
	}

	private long maxYPosition(CanvasAdapter canvas, Animation animation, Bunny player) {
		return player.getCenterY() + animation.getHeight(canvas) / 2;
	}

	private long minXPosition(CanvasAdapter canvas, Animation animation, Bunny player) {
		return player.getCenterX() - animation.getWidth(canvas) / 2;
	}

	private void drawMarkerAtBorder(CanvasAdapter canvas) {
		int centerOfMarkerX = getCenterOfBorderMarkerX(canvas);
		int centerOfMarkerY = getCenterOfBorderMarkerY(canvas);
		int width = 30;
		int height = 30;
		canvas.drawRectAbsoluteScreen(centerOfMarkerX - width / 2, centerOfMarkerY - height / 2, centerOfMarkerX
				+ width / 2, centerOfMarkerY + height / 2, paint);
	}

	private int getCenterOfBorderMarkerX(CanvasAdapter canvas) {
		if (canvas.isVisibleX(player.centerX()))
			return canvas.transformX(player.centerX());
		else {
			if (canvas.transformX(player.centerX()) < 0)
				return 0;
			return canvas.getOriginalWidth();
		}
	}

	private int getCenterOfBorderMarkerY(CanvasAdapter canvas) {
		if (canvas.isVisibleY(player.centerY()))
			return canvas.transformY(player.centerY());
		else {
			if (canvas.transformY(player.centerY()) < 0)
				return 0;
			return canvas.getOriginalHeight();
		}
	}

	@Override
	public boolean drawsPlayer(Bunny p) {
		return this.player.equals(p);
	}

}
