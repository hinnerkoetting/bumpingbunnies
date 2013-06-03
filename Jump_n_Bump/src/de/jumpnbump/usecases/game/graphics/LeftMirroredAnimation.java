package de.jumpnbump.usecases.game.graphics;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class LeftMirroredAnimation extends AbstractAnimation {

	public LeftMirroredAnimation(List<Bitmap> pictures, int timeBetweenPictures) {
		super(pictures, timeBetweenPictures);
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas, int width, int height) {
		this.scaledPictures.clear();
		for (int i = 0; i < this.pictures.size(); i++) {
			Bitmap original = this.pictures.get(i);
			Bitmap scaled = Bitmap.createScaledBitmap(original, width, height,
					false);
			Matrix mirror = new Matrix();
			mirror.preScale(-1.0f, 1.0f);
			Bitmap mirroredBitmap = Bitmap.createBitmap(scaled, 0, 0,
					scaled.getWidth(), scaled.getHeight(), mirror, false);
			this.scaledPictures.add(mirroredBitmap);
		}

	}

}
