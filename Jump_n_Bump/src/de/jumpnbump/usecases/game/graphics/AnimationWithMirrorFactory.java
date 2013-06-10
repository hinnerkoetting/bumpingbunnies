package de.jumpnbump.usecases.game.graphics;

import java.util.List;

import android.graphics.Bitmap;

public class AnimationWithMirrorFactory {

	public static AnimationWithMirror create(List<Bitmap> pictures,
			int timeBetweenPictures) {
		return new AnimationWithMirror(pictures, timeBetweenPictures);
	}

	public static AnimationWithMirror create(NormalAnimation pictures,
			Animation mirroredAnimation) {
		return new AnimationWithMirror(pictures, mirroredAnimation);
	}
}
