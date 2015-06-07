package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public abstract class ConditionalAnimation implements Animation {

	private final Animation animation;

	public ConditionalAnimation(Animation animation) {
		this.animation = animation;
	}

	public abstract boolean shouldBeExecuted(Bunny player);

	@Override
	public void draw(CanvasAdapter canvas, long left, long top, Paint paint) {
		this.animation.draw(canvas, left, top, paint);
	}
	
	@Override
	public void drawBlinking(CanvasAdapter canvas, long left, long top, Paint paint) {
		animation.drawBlinking(canvas, left, top, paint);
	}
}
