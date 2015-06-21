package de.oetting.bumpingbunnies.core.game.graphics.factory;

import java.util.List;

import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class PlayerImagesCache implements PlayerImagesProvider {

	private final PlayerImagesProvider provider;
	private int lastWidth;
	private int lastheight;
	
	private List<ImageWrapper> runningImages;
	private List<ImageWrapper> fallingImages;
	private List<ImageWrapper> jumpingImages;
	private List<ImageWrapper> sittingImages;
	private List<ImageWrapper> jumpingUpImages;

	public PlayerImagesCache(PlayerImagesProvider provider) {
		this.provider = provider;
	}

	public synchronized List<ImageWrapper> loadAllRunningImages(int width, int heigth) {
		storeWidthAndCheckCache(width, heigth);
		if (runningImages == null) 
			runningImages = provider.loadAllRunningImages(width, heigth);
		return runningImages;
	}

	private void storeWidthAndCheckCache(int width, int heigth) {
		if (width != lastWidth || heigth != lastheight) {
			runningImages = null;
			fallingImages = null;
			jumpingImages = null;
			sittingImages = null;
			jumpingUpImages = null;
			lastheight = heigth;
			lastWidth = width;
		}
	}

	public synchronized List<ImageWrapper> loadAllFallingImages(int width, int heigth) {
		storeWidthAndCheckCache(width, heigth);
		if (fallingImages == null)
			fallingImages = provider.loadAllFallingImages(width, heigth);
		return fallingImages;
	}

	public synchronized List<ImageWrapper> loadAllJumpingImages(int width, int heigth) {
		storeWidthAndCheckCache(width, heigth);
		if (jumpingImages == null)
			jumpingImages = provider.loadAllJumpingImages(width, heigth);
		return jumpingImages;
	}

	public synchronized List<ImageWrapper> loadAllSittingImages(int width, int heigth) {
		storeWidthAndCheckCache(width, heigth);
		if (sittingImages == null)
			sittingImages = provider.loadAllSittingImages(width, heigth);
		return sittingImages;
	}

	public synchronized List<ImageWrapper> loadAllJumpingUpImages(int width, int heigth) {
		storeWidthAndCheckCache(width, heigth);
		if (jumpingUpImages == null)
			jumpingUpImages = provider.loadAllJumpingUpImages(width, heigth);
		return jumpingUpImages;
	}

	public ImageWrapper loadOneImage(int width, int heigth) {
		return provider.loadOneImage(width, heigth);
	}

	@Override
	public ImageWrapper readSiggi() {
		return provider.readSiggi();
	}
	
	
}
