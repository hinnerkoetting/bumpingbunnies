package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;

public abstract class ConditionalMirroredAnimation extends ConditionalAnimation implements MirroredAnimation {

	private MirroredAnimation animation;

	public ConditionalMirroredAnimation(MirroredAnimation animation) {
		super(animation);
		this.animation = animation;
	}

	@Override
	public void drawMirrored(boolean b) {
		this.animation.drawMirrored(b);
	}

	@Override
	public int getWidth(CanvasAdapter canvas) {
		return animation.getWidth(canvas);
	}

	@Override
	public int getHeight(CanvasAdapter canvas) {
		return animation.getHeight(canvas);
	}
}
