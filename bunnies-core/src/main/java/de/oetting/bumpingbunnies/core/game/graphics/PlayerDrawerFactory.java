package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.graphics.calculation.ImagesColorer;
import de.oetting.bumpingbunnies.core.game.graphics.factory.PlayerImagesProvider;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class PlayerDrawerFactory {

	private final PlayerImagesProvider imagesProvider;
	private final ImagesColorer coloror;
	private final ImageMirroror mirroror;

	public PlayerDrawerFactory(PlayerImagesProvider imagesProvider, ImagesColorer coloror, ImageMirroror mirroror) {
		this.imagesProvider = imagesProvider;
		this.coloror = coloror;
		this.mirroror = mirroror;
	}

	public PlayerDrawer create(int width, int heigth, Player player) {
		int timeBetweenPictures = 50;
		ConditionalMirroredAnimation runningAnimation = AnimationWithMirrorFactory.createRunningAnimation(createRunningAnimation(width, heigth, player), timeBetweenPictures,
				mirroror);
		ConditionalMirroredAnimation fallingAnimation = AnimationWithMirrorFactory.createFallingAnimation(createFallingAnimation(width, heigth, player), timeBetweenPictures,
				mirroror);
		ConditionalMirroredAnimation jumpingAnimation = AnimationWithMirrorFactory.createJumpingAnimation(createJumpingAnimation(width, heigth, player), timeBetweenPictures,
				mirroror);
		ConditionalMirroredAnimation sittingAnimation = AnimationWithMirrorFactory.createSittingAnimation(createSittingAnimation(width, heigth, player), timeBetweenPictures,
				mirroror);
		ConditionalMirroredAnimation jumpingOnlyUpAnimation = AnimationWithMirrorFactory.createJumpingOnlyUpAnimation(
				createJumpingOnlyUpAnimation(width, heigth, player), 100, mirroror);
		List<ConditionalMirroredAnimation> animations = Arrays.asList(runningAnimation, fallingAnimation, jumpingAnimation, sittingAnimation,
				jumpingOnlyUpAnimation);
		return new PlayerDrawer(player, animations);
	}

	private List<ImageWrapper> createRunningAnimation(int width, int heigth, Player player) {
		List<ImageWrapper> originalBitmaps = imagesProvider.loadAllRunningImages(width, heigth);
		return colorImageWrappers(originalBitmaps, player);
	}

	private List<ImageWrapper> createFallingAnimation(int width, int heigth, Player player) {
		List<ImageWrapper> originalBitmaps = imagesProvider.loadAllFallingImages(width, heigth);
		return colorImageWrappers(originalBitmaps, player);
	}

	private List<ImageWrapper> createJumpingAnimation(int width, int heigth, Player player) {
		List<ImageWrapper> originalBitmaps = imagesProvider.loadAllJumpingUpImages(width, heigth);
		return colorImageWrappers(originalBitmaps, player);
	}

	private List<ImageWrapper> createSittingAnimation(int width, int heigth, Player player) {
		List<ImageWrapper> originalBitmaps = imagesProvider.loadAllSittingImages(width, heigth);
		return colorImageWrappers(originalBitmaps, player);
	}

	private List<ImageWrapper> createJumpingOnlyUpAnimation(int width, int heigth, Player player) {
		List<ImageWrapper> originalBitmaps = imagesProvider.loadAllJumpingUpImages(width, heigth);
		return colorImageWrappers(originalBitmaps, player);
	}

	private List<ImageWrapper> colorImageWrappers(List<ImageWrapper> originalBitmaps, Player player) {
		List<ImageWrapper> coloredBitmaps = new ArrayList<ImageWrapper>(originalBitmaps.size());
		for (ImageWrapper originalBitmap : originalBitmaps) {
			ImageWrapper coloredBitmap = coloror.colorImage(originalBitmap, player.getColor());
			coloredBitmaps.add(coloredBitmap);
		}
		return coloredBitmaps;
	}

}
