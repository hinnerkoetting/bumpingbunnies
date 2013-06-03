package de.jumpnbump.usecases.game.graphics;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Paint;

public abstract class AbstractAnimation implements Animation {

	protected final List<Bitmap> pictures;
	protected List<Bitmap> scaledPictures;
	private final int timeBetweenPictures;
	private long lastTimeDrawn;
	private int currentIndex;

	public AbstractAnimation(List<Bitmap> pictures, int timeBetweenPictures) {
		this.pictures = pictures;
		this.timeBetweenPictures = timeBetweenPictures;
		this.lastTimeDrawn = System.currentTimeMillis();
		this.scaledPictures = new ArrayList<Bitmap>(pictures.size());
	}

	@Override
	public void draw(CanvasDelegate canvas, int left, int top, Paint paint) {
		if (System.currentTimeMillis() - this.lastTimeDrawn >= this.timeBetweenPictures) {
			increaseIndex();
		}

		drawCurrentImage(canvas, left, top, paint);
	}

	private void drawCurrentImage(CanvasDelegate canvas, int left, int top,
			Paint paint) {
		canvas.drawImage(this.scaledPictures.get(this.currentIndex), left, top,
				paint);
	}

	public void increaseIndex() {
		this.currentIndex += 1;
		this.currentIndex = this.currentIndex % this.pictures.size();
	}
}
