package de.oetting.bumpingbunnies.usecases.game.graphics;

import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.graphics.Paint;

public abstract class ConditionalAnimation implements Animation {

	private final Animation animation;

	public ConditionalAnimation(Animation animation) {
		super();
		this.animation = animation;
	}

	public abstract boolean shouldBeExecuted();

	@Override
	public void updateGraphics(CanvasDelegate canvas, int width, int height) {
		this.animation.updateGraphics(canvas, width, height);
	}

	@Override
	public void draw(CanvasDelegate canvas, long left, long top, Paint paint) {
		this.animation.draw(canvas, left, top, paint);
	}
}
