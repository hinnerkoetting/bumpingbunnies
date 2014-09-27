package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.List;

import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class AnimationWithMirrorFactory {

	public static ConditionalMirroredAnimation createRunningAnimation(final Player player, final List<ImageWrapper> pictures, final int timeBetweenPictures,
			ImageResizer imageResizer, ImageResizer mirrorImageResizer) {
		MirroredAnimation animationImpl = create(pictures, timeBetweenPictures, imageResizer, mirrorImageResizer);
		return new ConditionalMirroredAnimation(animationImpl) {

			@Override
			public boolean shouldBeExecuted() {
				return (Math.abs(player.movementX()) >= ModelConstants.MOVEMENT_LIMIT) && Math.abs(player.movementY()) <= ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static ConditionalMirroredAnimation createFallingAnimation(final Player player, final List<ImageWrapper> pictures, final int timeBetweenPictures,
			ImageResizer imageResizer, ImageResizer mirrorImageResizer) {
		MirroredAnimation animationImpl = create(pictures, timeBetweenPictures, imageResizer, mirrorImageResizer);
		return new ConditionalMirroredAnimation(animationImpl) {

			@Override
			public boolean shouldBeExecuted() {
				return player.movementY() <= -ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static ConditionalMirroredAnimation createJumpingAnimation(final Player player, final List<ImageWrapper> pictures, final int timeBetweenPictures,
			ImageResizer imageResizer, ImageResizer mirrorImageResizer) {
		MirroredAnimation animationImpl = create(pictures, timeBetweenPictures, imageResizer, mirrorImageResizer);
		return new ConditionalMirroredAnimation(animationImpl) {

			@Override
			public boolean shouldBeExecuted() {
				return player.movementY() >= ModelConstants.MOVEMENT_LIMIT && Math.abs(player.movementX()) >= ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static ConditionalMirroredAnimation createSittingAnimation(final Player player, final List<ImageWrapper> pictures, final int timeBetweenPictures,
			ImageResizer imageResizer, ImageResizer mirrorImageResizer) {
		MirroredAnimation animationImpl = create(pictures, timeBetweenPictures, imageResizer, mirrorImageResizer);
		return new ConditionalMirroredAnimation(animationImpl) {

			@Override
			public boolean shouldBeExecuted() {
				return Math.abs(player.movementX()) <= ModelConstants.MOVEMENT_LIMIT && Math.abs(player.movementY()) <= ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static ConditionalMirroredAnimation createJumpingOnlyUpAnimation(final Player player, final List<ImageWrapper> pictures,
			final int timeBetweenPictures, ImageResizer imageResizer, ImageResizer mirrorImageResizer) {
		MirroredAnimation animationImpl = create(pictures, timeBetweenPictures, imageResizer, mirrorImageResizer);
		return new ConditionalMirroredAnimation(animationImpl) {

			@Override
			public boolean shouldBeExecuted() {
				return Math.abs(player.movementX()) <= ModelConstants.MOVEMENT_LIMIT && player.movementY() >= ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static MirroredAnimation create(List<ImageWrapper> pictures, int timeBetweenPictures, ImageResizer imageResizer, ImageResizer mirrorImageResizer) {
		return new AnimationWithMirror(pictures, timeBetweenPictures, imageResizer, mirrorImageResizer);
	}

	public static MirroredAnimation create(Animation pictures, Animation mirroredAnimation) {
		return new AnimationWithMirror(pictures, mirroredAnimation);
	}
}
