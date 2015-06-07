package de.oetting.bumpingbunnies.core.game.graphics;

import de.oetting.bumpingbunnies.core.graphics.Paint;

public class AnimationWithMirror implements Animation, MirroredAnimation {

	private Animation normalAnimation;
	private Animation leftMirroredAnimation;
	private boolean drawNormal = true;

	public AnimationWithMirror(Animation normalAnimation, Animation leftMirroredAnimation) {
		this.normalAnimation = normalAnimation;
		this.leftMirroredAnimation = leftMirroredAnimation;
	}

	@Override
	public void draw(CanvasAdapter canvas, long left, long top, Paint paint) {
		if (this.drawNormal) {
			this.normalAnimation.draw(canvas, left, top, paint);
		} else {
			this.leftMirroredAnimation.draw(canvas, left, top, paint);
		}
	}
	
	@Override
	public void drawBlinking(CanvasAdapter canvas, long left, long top, Paint paint) {
		if (this.drawNormal) {
			this.normalAnimation.drawBlinking(canvas, left, top, paint);
		} else {
			this.leftMirroredAnimation.drawBlinking(canvas, left, top, paint);
		}		
	}

	@Override
	public void drawMirrored(boolean b) {
		this.drawNormal = !b;
	}

	@Override
	public int getWidth(CanvasAdapter canvas) {
		return normalAnimation.getWidth(canvas);
	}

	@Override
	public int getHeight(CanvasAdapter canvas) {
		return normalAnimation.getHeight(canvas);
	}
}
