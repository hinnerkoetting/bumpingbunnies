package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.model.game.objects.HorizontalMovementState;
import de.oetting.bumpingbunnies.model.game.objects.BunnyImage;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class AnimationWithMirrorFactory {

	public static ConditionalMirroredAnimation createRunningAnimation(final List<BunnyImage> pictures,
			final int timeBetweenPictures, ImageMirroror mirroror) {
		MirroredAnimation completeAnimation = createAnimation(pictures, timeBetweenPictures, mirroror);
		return new ConditionalMirroredAnimation(completeAnimation) {

			@Override
			public boolean shouldBeExecuted(Bunny player) {
				return isMovingHorizontally(player) && !isMovingVertically(player);
			}

		};
	}

	public static ConditionalMirroredAnimation createFallingAnimation(final List<BunnyImage> pictures,
			final int timeBetweenPictures, ImageMirroror mirroror) {
		MirroredAnimation completeAnimation = createAnimation(pictures, timeBetweenPictures, mirroror);
		return new ConditionalMirroredAnimation(completeAnimation) {

			@Override
			public boolean shouldBeExecuted(Bunny player) {
				return isFalling(player);
			}

		};
	}

	public static ConditionalMirroredAnimation createJumpingAnimation(final List<BunnyImage> pictures,
			final int timeBetweenPictures, ImageMirroror mirroror) {
		MirroredAnimation completeAnimation = createAnimation(pictures, timeBetweenPictures, mirroror);
		return new ConditionalMirroredAnimation(completeAnimation) {

			@Override
			public boolean shouldBeExecuted(Bunny player) {
				return isGoingUp(player) && isMovingHorizontally(player);
			}
		};
	}

	public static ConditionalMirroredAnimation createSittingAnimation(final List<BunnyImage> pictures,
			final int timeBetweenPictures, ImageMirroror mirroror) {
		MirroredAnimation completeAnimation = createAnimation(pictures, timeBetweenPictures, mirroror);
		return new ConditionalMirroredAnimation(completeAnimation) {

			@Override
			public boolean shouldBeExecuted(Bunny player) {
				return !isMovingHorizontally(player) && !isMovingVertically(player);
			}
		};
	}

	public static ConditionalMirroredAnimation createJumpingOnlyUpAnimation(final List<BunnyImage> pictures,
			final int timeBetweenPictures, ImageMirroror mirroror) {
		MirroredAnimation completeAnimation = createAnimation(pictures, timeBetweenPictures, mirroror);
		return new ConditionalMirroredAnimation(completeAnimation) {

			@Override
			public boolean shouldBeExecuted(Bunny player) {
				return !isMovingHorizontally(player) && isMovingVertically(player);
			}
		};
	}

	private static MirroredAnimation createAnimation(final List<BunnyImage> pictures, final int timeBetweenPictures,
			ImageMirroror mirroror) {
		List<BunnyImage> normalAnimation = createAnimation(pictures);
		List<BunnyImage> mirroredAnimation = createMirroredAnimation(pictures, mirroror);
		return create(new DefaultAnimation(normalAnimation, timeBetweenPictures), new DefaultAnimation(
				mirroredAnimation, timeBetweenPictures));
	}

	private static List<BunnyImage> createAnimation(List<BunnyImage> originalPictures) {
		List<BunnyImage> images = new ArrayList<BunnyImage>(originalPictures.size());
		for (int i = 0; i < originalPictures.size(); i++) {
			BunnyImage original = originalPictures.get(i);
			images.add(original);
		}
		return images;
	}

	private static List<BunnyImage> createMirroredAnimation(List<BunnyImage> originalPictures,
			ImageMirroror mirrorer) {
		List<BunnyImage> images = new ArrayList<BunnyImage>(originalPictures.size());
		for (int i = 0; i < originalPictures.size(); i++) {
			BunnyImage original = originalPictures.get(i);
			images.add(new BunnyImage(mirrorer.mirrorImage(original.getImage()), original.getModel()));
		}
		return images;
	}

	public static MirroredAnimation create(Animation pictures, Animation mirroredAnimation) {
		return new AnimationWithMirror(pictures, mirroredAnimation);
	}

	private static boolean isMovingHorizontally(Bunny bunny) {
		return !bunny.getState().getHorizontalMovementStatus().equals(HorizontalMovementState.NOT_MOVING_HORIZONTAL);
	}

	private static boolean isMovingVertically(Bunny player) {
		return Math.abs(player.movementY()) >= ModelConstants.MOVEMENT_LIMIT;
	}

	private static boolean isFalling(Bunny player) {
		return player.movementY() <= -ModelConstants.MOVEMENT_LIMIT;
	}

	private static boolean isGoingUp(Bunny player) {
		return player.movementY() >= ModelConstants.MOVEMENT_LIMIT;
	}
}
