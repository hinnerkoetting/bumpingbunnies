package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;

public abstract class ConditionalAnimation implements Animation {

	private final Animation animation;

	public ConditionalAnimation(Animation animation) {
		super();
		this.animation = animation;
	}

	public abstract boolean shouldBeExecuted();

	@Override
	public void draw(CanvasDelegate canvas, long left, long top, Paint paint) {
		this.animation.draw(canvas, left, top, paint);
	}
}
