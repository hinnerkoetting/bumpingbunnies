package de.jumpnbump.usecases.game.graphics;

import java.util.List;

import android.graphics.Bitmap;

public class NormalAnimation extends AbstractAnimation {

	public NormalAnimation(List<Bitmap> pictures, int timeBetweenPictures) {
		super(pictures, timeBetweenPictures);
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas, int width, int height) {
		this.scaledPictures.clear();
		for (int i = 0; i < this.pictures.size(); i++) {
			Bitmap original = this.pictures.get(i);
			Bitmap scaled = Bitmap.createScaledBitmap(original, width, height,
					false);
			this.scaledPictures.add(scaled);
		}

	}
}
