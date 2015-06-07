package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.List;

import de.oetting.bumpingbunnies.core.graphics.Paint;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class DefaultAnimation implements Animation {

	protected final List<ImageWrapper> originalPictures;
	private final int timeBetweenPictures;
	// protected List<ImageWrapper> scaledPictures;
	private long lastTimeSwitched;
	private int currentIndex;
	// private ImageResizer resizer;
	private boolean movingIndexUp = true;

	public DefaultAnimation(List<ImageWrapper> pictures, int timeBetweenPictures) {
		this.originalPictures = pictures;
		this.timeBetweenPictures = timeBetweenPictures;
		this.lastTimeSwitched = System.currentTimeMillis();
		if (this.originalPictures.size() == 0) {
			throw new NoImagesInAnimation();
		}
	}

	@Override
	public void draw(CanvasAdapter canvas, long left, long top, Paint paint) {
		switchImageIfNecessary();

		drawCurrentImage(canvas, left, top, paint);
	}

	@Override
	public void drawBlinking(CanvasAdapter canvas, long left, long top, Paint paint) {
		switchImageIfNecessary();
		drawCurrentImageBlinking(canvas, left, top, paint);
	}

	private void switchImageIfNecessary() {
		long currentTime = System.currentTimeMillis();
		if (currentTime - this.lastTimeSwitched >= this.timeBetweenPictures) {
			changeIndex();
			this.lastTimeSwitched = currentTime;
		}
	}

	private void drawCurrentImage(CanvasAdapter canvas, long left, long top, Paint paint) {
		canvas.drawImage(this.originalPictures.get(this.currentIndex), left, top, paint);
	}

	private void drawCurrentImageBlinking(CanvasAdapter canvas, long left, long top, Paint paint) {
		canvas.drawImageBlinking(this.originalPictures.get(this.currentIndex), left, top, paint);
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

	public static class NoImagesInAnimation extends RuntimeException {
	}

	@Override
	public int getWidth(CanvasAdapter canvas) {
		return canvas.getWidth(this.originalPictures.get(this.currentIndex));
	}

	@Override
	public int getHeight(CanvasAdapter canvas) {
		return canvas.getHeight(this.originalPictures.get(this.currentIndex));
	}
}
