package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.graphics.bitmapAltering.BitmapResizer;

public class AnimationImpl implements Animation {

	protected final List<Bitmap> originalPictures;
	private final int timeBetweenPictures;
	protected List<Bitmap> scaledPictures;
	private long lastTimeSwitched;
	private int currentIndex;
	private BitmapResizer resizer;
	private boolean movingIndexUp = true;

	public AnimationImpl(List<Bitmap> pictures, int timeBetweenPictures,
			BitmapResizer bitmapResizer) {
		this.originalPictures = pictures;
		this.timeBetweenPictures = timeBetweenPictures;
		this.resizer = bitmapResizer;
		this.lastTimeSwitched = System.currentTimeMillis();
		this.scaledPictures = new ArrayList<Bitmap>(pictures.size());
	}

	@Override
	public void draw(CanvasDelegate canvas, long left, long top, Paint paint) {
		long currentTime = System.currentTimeMillis();
		if (currentTime - this.lastTimeSwitched >= this.timeBetweenPictures) {
			changeIndex();
			this.lastTimeSwitched = currentTime;
		}

		drawCurrentImage(canvas, left, top, paint);
	}

	private void drawCurrentImage(CanvasDelegate canvas, long left, long top,
			Paint paint) {
		canvas.drawImage(this.scaledPictures.get(this.currentIndex), left, top,
				paint);
	}

	public void changeIndex() {
		if (this.movingIndexUp && this.currentIndex == this.originalPictures.size() - 1) {
			this.movingIndexUp = false;
		}
		if (!this.movingIndexUp && this.currentIndex == 0) {
			this.movingIndexUp = true;
		}
		this.currentIndex += this.movingIndexUp ? 1 : -1;
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas, int width, int height) {
		this.scaledPictures.clear();
		for (int i = 0; i < this.originalPictures.size(); i++) {
			Bitmap original = this.originalPictures.get(i);
			Bitmap resized = this.resizer.resize(original, width, height);
			this.scaledPictures.add(resized);
		}
	}
}
