package de.oetting.bumpingbunnies.usecases.game.graphics;

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

}
