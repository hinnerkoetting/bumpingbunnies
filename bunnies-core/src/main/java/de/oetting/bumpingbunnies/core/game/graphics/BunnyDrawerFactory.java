package de.oetting.bumpingbunnies.core.game.graphics;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.graphics.calculation.HeadDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.ImagesColorer;
import de.oetting.bumpingbunnies.core.game.graphics.factory.PlayerImagesCache;
import de.oetting.bumpingbunnies.core.game.graphics.factory.PlayerImagesProvider;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.BunnyImageModel;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.worldcreator.load.ClasspathImageReader;

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

	private List<ImageWrapper> createRunningAnimation(int width, int heigth, Bunny player) {
		List<ImageWrapper> originalBitmaps = imagesProvider.loadAllRunningImages(width, heigth);
		return colorImageWrappers(originalBitmaps, player);
	}

	private List<ImageWrapper> createFallingAnimation(int width, int heigth, Bunny player) {
		List<ImageWrapper> originalBitmaps = imagesProvider.loadAllFallingImages(width, heigth);
		return colorImageWrappers(originalBitmaps, player);
	}

	private List<ImageWrapper> createJumpingAnimation(int width, int heigth, Bunny player) {
		List<ImageWrapper> originalBitmaps = imagesProvider.loadAllJumpingUpImages(width, heigth);
		return colorImageWrappers(originalBitmaps, player);
	}

	private List<ImageWrapper> createSittingAnimation(int width, int heigth, Bunny player) {
		List<ImageWrapper> originalBitmaps = imagesProvider.loadAllSittingImages(width, heigth);
		return colorImageWrappers(originalBitmaps, player);
	}

	private List<ImageWrapper> createJumpingOnlyUpAnimation(int width, int heigth, Bunny player) {
		List<ImageWrapper> originalBitmaps = imagesProvider.loadAllJumpingUpImages(width, heigth);
		return colorImageWrappers(originalBitmaps, player);
	}

	private List<ImageWrapper> colorImageWrappers(List<ImageWrapper> originalBitmaps, Bunny player) {
		List<ImageWrapper> coloredBitmaps = new ArrayList<ImageWrapper>(originalBitmaps.size());
		for (ImageWrapper originalBitmap : originalBitmaps) {
			ImageWrapper coloredBitmap = coloror.colorImage(originalBitmap, player.getColor());
			ImageWrapper bitmapWithImage = headDrawer.overDrawFace(coloredBitmap, imagesProvider.readSiggi(),
					new BunnyImageModel(66, 61, 28, 100, 100));
			coloredBitmaps.add(bitmapWithImage);
		}
		return coloredBitmaps;
	}

}
