package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class AnimationWithMirrorFactory {

	public static ConditionalMirroredAnimation createRunningAnimation(final List<ImageWrapper> pictures, final int timeBetweenPictures, ImageMirroror mirroror) {
		MirroredAnimation completeAnimation = createAnimation(pictures, timeBetweenPictures, mirroror);
		return new ConditionalMirroredAnimation(completeAnimation) {

			@Override
			public boolean shouldBeExecuted(Bunny player) {
				return Math.abs(player.movementX()) >= ModelConstants.MOVEMENT_LIMIT && Math.abs(player.movementY()) <= ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static ConditionalMirroredAnimation createFallingAnimation(final List<ImageWrapper> pictures, final int timeBetweenPictures, ImageMirroror mirroror) {
		MirroredAnimation completeAnimation = createAnimation(pictures, timeBetweenPictures, mirroror);
		return new ConditionalMirroredAnimation(completeAnimation) {

			@Override
			public boolean shouldBeExecuted(Bunny player) {
				return player.movementY() <= -ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static ConditionalMirroredAnimation createJumpingAnimation(final List<ImageWrapper> pictures, final int timeBetweenPictures, ImageMirroror mirroror) {
		MirroredAnimation completeAnimation = createAnimation(pictures, timeBetweenPictures, mirroror);
		return new ConditionalMirroredAnimation(completeAnimation) {

			@Override
			public boolean shouldBeExecuted(Bunny player) {
				return player.movementY() >= ModelConstants.MOVEMENT_LIMIT && Math.abs(player.movementX()) >= ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static ConditionalMirroredAnimation createSittingAnimation(final List<ImageWrapper> pictures, final int timeBetweenPictures, ImageMirroror mirroror) {
		MirroredAnimation completeAnimation = createAnimation(pictures, timeBetweenPictures, mirroror);
		return new ConditionalMirroredAnimation(completeAnimation) {

			@Override
			public boolean shouldBeExecuted(Bunny player) {
				return Math.abs(player.movementX()) <= ModelConstants.MOVEMENT_LIMIT && Math.abs(player.movementY()) <= ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static ConditionalMirroredAnimation createJumpingOnlyUpAnimation(final List<ImageWrapper> pictures, final int timeBetweenPictures,
			ImageMirroror mirroror) {
		MirroredAnimation completeAnimation = createAnimation(pictures, timeBetweenPictures, mirroror);
		return new ConditionalMirroredAnimation(completeAnimation) {

			@Override
			public boolean shouldBeExecuted(Bunny player) {
				return Math.abs(player.movementX()) <= ModelConstants.MOVEMENT_LIMIT && player.movementY() >= ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	private static MirroredAnimation createAnimation(final List<ImageWrapper> pictures, final int timeBetweenPictures, ImageMirroror mirroror) {
		List<ImageWrapper> normalAnimation = createAnimation(pictures);
		List<ImageWrapper> mirroredAnimation = createMirroredAnimation(pictures, mirroror);
		return create(new AnimationImpl(normalAnimation, timeBetweenPictures), new AnimationImpl(mirroredAnimation, timeBetweenPictures));
	}

	private static List<ImageWrapper> createAnimation(List<ImageWrapper> originalPictures) {
		List<ImageWrapper> images = new ArrayList<ImageWrapper>(originalPictures.size());
		for (int i = 0; i < originalPictures.size(); i++) {
			ImageWrapper original = originalPictures.get(i);
			images.add(original);
		}
		return images;
	}

	private static List<ImageWrapper> createMirroredAnimation(List<ImageWrapper> originalPictures, ImageMirroror mirrorer) {
		List<ImageWrapper> images = new ArrayList<ImageWrapper>(originalPictures.size());
		for (int i = 0; i < originalPictures.size(); i++) {
			ImageWrapper original = originalPictures.get(i);
			images.add(mirrorer.mirrorImage(original));
		}
		return images;
	}

	public static MirroredAnimation create(Animation pictures, Animation mirroredAnimation) {
		return new AnimationWithMirror(pictures, mirroredAnimation);
	}
}
