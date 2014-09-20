package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.graphics.bitmapAltering.MirrorBitmapResizer;
import de.oetting.bumpingbunnies.usecases.game.graphics.bitmapAltering.SimpleBitmapResizer;

public class AnimationWithMirror implements Animation, MirroredAnimation {

	private Animation normalAnimation;
	private Animation leftMirroredAnimation;
	private boolean drawNormal = true;

	public AnimationWithMirror(List<Bitmap> pictures, int timeBetweenPictures) {
		this.normalAnimation = new AnimationImpl(pictures, timeBetweenPictures,
				new SimpleBitmapResizer());
		this.leftMirroredAnimation = new AnimationImpl(pictures,
				timeBetweenPictures, new MirrorBitmapResizer());
	}

	public AnimationWithMirror(Animation normalAnimation,
			Animation leftMirroredAnimation) {
		this.normalAnimation = normalAnimation;
		this.leftMirroredAnimation = leftMirroredAnimation;
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas, int width, int height) {
		this.normalAnimation.updateGraphics(canvas, width, height);
		this.leftMirroredAnimation.updateGraphics(canvas, width, height);
	}

	@Override
	public void draw(CanvasDelegate canvas, long left, long top, Paint paint) {
		if (this.drawNormal) {
			this.normalAnimation.draw(canvas, left, top, paint);
		} else {
			this.leftMirroredAnimation.draw(canvas, left, top, paint);
		}

	}

	@Override
	public void drawMirrored(boolean b) {
		this.drawNormal = !b;
	}
}