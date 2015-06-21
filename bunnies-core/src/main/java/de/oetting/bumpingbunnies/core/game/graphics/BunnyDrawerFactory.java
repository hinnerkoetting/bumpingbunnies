package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.graphics.calculation.HeadDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.ImagesColorer;
import de.oetting.bumpingbunnies.core.game.graphics.factory.PlayerImagesCache;
import de.oetting.bumpingbunnies.core.game.graphics.factory.PlayerImagesProvider;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.objects.BunnyImage;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class BunnyDrawerFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(BunnyDrawerFactory.class);
	private final PlayerImagesProvider imagesProvider;
	private final ImagesColorer coloror;
	private final ImageMirroror mirroror;
	private final HeadDrawer headDrawer;

	public BunnyDrawerFactory(PlayerImagesProvider imagesProvider, ImagesColorer coloror, ImageMirroror mirroror,
			HeadDrawer headDrawer) {
		this.headDrawer = headDrawer;
		this.imagesProvider = new PlayerImagesCache(imagesProvider);
		this.coloror = coloror;
		this.mirroror = mirroror;
	}

	public BunnyDrawer create(int width, int heigth, Bunny player) {
		long timeBefore = System.currentTimeMillis();
		int timeBetweenPictures = 25;
		ConditionalMirroredAnimation runningAnimation = AnimationWithMirrorFactory.createRunningAnimation(
				createRunningAnimation(width, heigth, player), timeBetweenPictures, mirroror);
		ConditionalMirroredAnimation fallingAnimation = AnimationWithMirrorFactory.createFallingAnimation(
				createFallingAnimation(width, heigth, player), timeBetweenPictures, mirroror);
		ConditionalMirroredAnimation jumpingAnimation = AnimationWithMirrorFactory.createJumpingAnimation(
				createJumpingAnimation(width, heigth, player), timeBetweenPictures, mirroror);
		ConditionalMirroredAnimation sittingAnimation = AnimationWithMirrorFactory.createSittingAnimation(
				createSittingAnimation(width, heigth, player), timeBetweenPictures, mirroror);
		ConditionalMirroredAnimation jumpingOnlyUpAnimation = AnimationWithMirrorFactory.createJumpingOnlyUpAnimation(
				createJumpingOnlyUpAnimation(width, heigth, player), 100, mirroror);
		List<ConditionalMirroredAnimation> animations = Arrays.asList(runningAnimation, fallingAnimation,
				jumpingAnimation, sittingAnimation, jumpingOnlyUpAnimation);
		LOGGER.info("Creating image of bunny took %d milliseconds", System.currentTimeMillis() - timeBefore);
		return new BunnyDrawer(player, animations);
	}

	private List<BunnyImage> createRunningAnimation(int width, int heigth, Bunny player) {
		List<BunnyImage> originalBitmaps = imagesProvider.loadAllRunningImages(width, heigth);
		return colorImageWrappers(originalBitmaps, player);
	}

	private List<BunnyImage> createFallingAnimation(int width, int heigth, Bunny player) {
		List<BunnyImage> originalBitmaps = imagesProvider.loadAllFallingImages(width, heigth);
		return colorImageWrappers(originalBitmaps, player);
	}

	private List<BunnyImage> createJumpingAnimation(int width, int heigth, Bunny player) {
		List<BunnyImage> originalBitmaps = imagesProvider.loadAllJumpingUpImages(width, heigth);
		return colorImageWrappers(originalBitmaps, player);
	}

	private List<BunnyImage> createSittingAnimation(int width, int heigth, Bunny player) {
		List<BunnyImage> originalBitmaps = imagesProvider.loadAllSittingImages(width, heigth);
		return colorImageWrappers(originalBitmaps, player);
	}

	private List<BunnyImage> createJumpingOnlyUpAnimation(int width, int heigth, Bunny player) {
		List<BunnyImage> originalBitmaps = imagesProvider.loadAllJumpingUpImages(width, heigth);
		return colorImageWrappers(originalBitmaps, player);
	}

	private List<BunnyImage> colorImageWrappers(List<BunnyImage> originalBitmaps, Bunny player) {
		List<BunnyImage> coloredBitmaps = new ArrayList<BunnyImage>(originalBitmaps.size());
		for (BunnyImage originalBitmap : originalBitmaps) {
			ImageWrapper coloredBitmap = coloror.colorImage(originalBitmap.getImage(), player.getColor());
			ImageWrapper face = imagesProvider.readImageForBunny(player);
			if (face != null) {
				ImageWrapper bitmapWithImage = headDrawer.overDrawFace(coloredBitmap, face,
						originalBitmap.getModel());
				coloredBitmaps.add(new BunnyImage(bitmapWithImage, originalBitmap.getModel()));
			} else 
				coloredBitmaps.add(new BunnyImage(coloredBitmap, originalBitmap.getModel()));
			
		}
		return coloredBitmaps;
	}
	

}
