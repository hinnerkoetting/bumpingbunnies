package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

public class AnimationImpl implements Animation {

	protected final List<ImageWrapper> originalPictures;
	private final int timeBetweenPictures;
	protected List<ImageWrapper> scaledPictures;
	private long lastTimeSwitched;
	private int currentIndex;
	private ImageResizer resizer;
	private boolean movingIndexUp = true;

	public AnimationImpl(List<ImageWrapper> pictures, int timeBetweenPictures, ImageResizer bitmapResizer) {
		this.originalPictures = pictures;
		this.timeBetweenPictures = timeBetweenPictures;
		this.resizer = bitmapResizer;
		this.lastTimeSwitched = System.currentTimeMillis();
		this.scaledPictures = new ArrayList<ImageWrapper>(pictures.size());
		if (this.originalPictures.size() == 0) {
			throw new NoImagesInAnimation();
		}
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

	private void drawCurrentImage(CanvasDelegate canvas, long left, long top, Paint paint) {
		canvas.drawImage(this.scaledPictures.get(this.currentIndex), left, top, paint);
	}

	public void changeIndex() {
		if (this.originalPictures.size() == 1) {
			this.currentIndex = 0;
		} else {
			if (this.movingIndexUp && this.currentIndex == this.originalPictures.size() - 1) {
				this.movingIndexUp = false;
			}
			if (!this.movingIndexUp && this.currentIndex == 0) {
				this.movingIndexUp = true;
			}
			this.currentIndex += this.movingIndexUp ? 1 : -1;
		}
	}

	@Override
	public void updateGraphics(CanvasDelegate canvas, int width, int height) {
		this.scaledPictures.clear();
		for (int i = 0; i < this.originalPictures.size(); i++) {
			ImageWrapper original = this.originalPictures.get(i);
			ImageWrapper resized = this.resizer.resize(original, width, height);
			this.scaledPictures.add(resized);
		}
	}

	public static class NoImagesInAnimation extends RuntimeException {
	}
}
