package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class AnimationWithMirrorFactory {

	public static ConditionalMirroredAnimation createRunningAnimation(final Player player, final List<ImageWrapper> pictures, final int timeBetweenPictures,
			ImageResizer imageResizer, ImageResizer mirrorImageResizer, CanvasDelegate canvas) {
		MirroredAnimation completeAnimation = createAnimation(player, pictures, timeBetweenPictures, imageResizer, mirrorImageResizer, canvas);
		return new ConditionalMirroredAnimation(completeAnimation) {

			@Override
			public boolean shouldBeExecuted() {
				return (Math.abs(player.movementX()) >= ModelConstants.MOVEMENT_LIMIT) && Math.abs(player.movementY()) <= ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static ConditionalMirroredAnimation createFallingAnimation(final Player player, final List<ImageWrapper> pictures, final int timeBetweenPictures,
			ImageResizer imageResizer, ImageResizer mirrorImageResizer, CanvasDelegate canvas) {
		MirroredAnimation completeAnimation = createAnimation(player, pictures, timeBetweenPictures, imageResizer, mirrorImageResizer, canvas);
		return new ConditionalMirroredAnimation(completeAnimation) {

			@Override
			public boolean shouldBeExecuted() {
				return player.movementY() <= -ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static ConditionalMirroredAnimation createJumpingAnimation(final Player player, final List<ImageWrapper> pictures, final int timeBetweenPictures,
			ImageResizer imageResizer, ImageResizer mirrorImageResizer, CanvasDelegate canvas) {
		MirroredAnimation completeAnimation = createAnimation(player, pictures, timeBetweenPictures, imageResizer, mirrorImageResizer, canvas);
		return new ConditionalMirroredAnimation(completeAnimation) {

			@Override
			public boolean shouldBeExecuted() {
				return player.movementY() >= ModelConstants.MOVEMENT_LIMIT && Math.abs(player.movementX()) >= ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static ConditionalMirroredAnimation createSittingAnimation(final Player player, final List<ImageWrapper> pictures, final int timeBetweenPictures,
			ImageResizer imageResizer, ImageResizer mirrorImageResizer, CanvasDelegate canvas) {
		MirroredAnimation completeAnimation = createAnimation(player, pictures, timeBetweenPictures, imageResizer, mirrorImageResizer, canvas);
		return new ConditionalMirroredAnimation(completeAnimation) {

			@Override
			public boolean shouldBeExecuted() {
				return Math.abs(player.movementX()) <= ModelConstants.MOVEMENT_LIMIT && Math.abs(player.movementY()) <= ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static ConditionalMirroredAnimation createJumpingOnlyUpAnimation(final Player player, final List<ImageWrapper> pictures,
			final int timeBetweenPictures, ImageResizer imageResizer, ImageResizer mirrorImageResizer, CanvasDelegate canvas) {
		MirroredAnimation completeAnimation = createAnimation(player, pictures, timeBetweenPictures, imageResizer, mirrorImageResizer, canvas);
		return new ConditionalMirroredAnimation(completeAnimation) {

			@Override
			public boolean shouldBeExecuted() {
				return Math.abs(player.movementX()) <= ModelConstants.MOVEMENT_LIMIT && player.movementY() >= ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	private static MirroredAnimation createAnimation(final Player player, final List<ImageWrapper> pictures, final int timeBetweenPictures,
			ImageResizer imageResizer, ImageResizer mirrorImageResizer, CanvasDelegate canvas) {
		List<ImageWrapper> normalAnimation = createAnimation(pictures, imageResizer, canvas, player);
		List<ImageWrapper> mirroredAnimation = createAnimation(pictures, mirrorImageResizer, canvas, player);
		return create(new AnimationImpl(normalAnimation, timeBetweenPictures), new AnimationImpl(mirroredAnimation, timeBetweenPictures));
	}

	private static List<ImageWrapper> createAnimation(List<ImageWrapper> originalPictures, ImageResizer imageResizer, CanvasDelegate canvas, Player player) {
		List<ImageWrapper> images = new ArrayList<ImageWrapper>(originalPictures.size());
		for (int i = 0; i < originalPictures.size(); i++) {
			ImageWrapper original = originalPictures.get(i);
			int width = (int) (canvas.transformX(player.maxX()) - canvas.transformX(player.minX()));
			int height = (int) (canvas.transformY(player.minY()) - canvas.transformY(player.maxY()));
			ImageWrapper resized = imageResizer.resize(original, width, height);
			images.add(resized);
		}
		return images;
	}

	public static MirroredAnimation create(Animation pictures, Animation mirroredAnimation) {
		return new AnimationWithMirror(pictures, mirroredAnimation);
	}
}
