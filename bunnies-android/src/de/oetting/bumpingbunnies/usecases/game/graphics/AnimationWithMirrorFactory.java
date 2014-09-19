package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.List;

import android.graphics.Bitmap;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class AnimationWithMirrorFactory {

	public static ConditionalMirroredAnimation createRunningAnimation(final Player player, final List<Bitmap> pictures,
			final int timeBetweenPictures) {
		MirroredAnimation animationImpl = create(pictures, timeBetweenPictures);
		return new ConditionalMirroredAnimation(animationImpl) {

			@Override
			public boolean shouldBeExecuted() {
				return (Math.abs(player.movementX()) >= ModelConstants.MOVEMENT_LIMIT)
						&& Math.abs(player.movementY()) <= ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static ConditionalMirroredAnimation createFallingAnimation(final Player player, final List<Bitmap> pictures,
			final int timeBetweenPictures) {
		MirroredAnimation animationImpl = create(pictures, timeBetweenPictures);
		return new ConditionalMirroredAnimation(animationImpl) {

			@Override
			public boolean shouldBeExecuted() {
				return player.movementY() <= -ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static ConditionalMirroredAnimation createJumpingAnimation(final Player player, final List<Bitmap> pictures,
			final int timeBetweenPictures) {
		MirroredAnimation animationImpl = create(pictures, timeBetweenPictures);
		return new ConditionalMirroredAnimation(animationImpl) {

			@Override
			public boolean shouldBeExecuted() {
				return player.movementY() >= ModelConstants.MOVEMENT_LIMIT && Math.abs(player.movementX()) >= ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static ConditionalMirroredAnimation createSittingAnimation(final Player player, final List<Bitmap> pictures,
			final int timeBetweenPictures) {
		MirroredAnimation animationImpl = create(pictures, timeBetweenPictures);
		return new ConditionalMirroredAnimation(animationImpl) {

			@Override
			public boolean shouldBeExecuted() {
				return Math.abs(player.movementX()) <= ModelConstants.MOVEMENT_LIMIT
						&& Math.abs(player.movementY()) <= ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static ConditionalMirroredAnimation createJumpingOnlyUpAnimation(final Player player, final List<Bitmap> pictures,
			final int timeBetweenPictures) {
		MirroredAnimation animationImpl = create(pictures, timeBetweenPictures);
		return new ConditionalMirroredAnimation(animationImpl) {

			@Override
			public boolean shouldBeExecuted() {
				return Math.abs(player.movementX()) <= ModelConstants.MOVEMENT_LIMIT
						&& player.movementY() >= ModelConstants.MOVEMENT_LIMIT;
			}
		};
	}

	public static MirroredAnimation create(List<Bitmap> pictures,
			int timeBetweenPictures) {
		return new AnimationWithMirror(pictures, timeBetweenPictures);
	}

	public static MirroredAnimation create(Animation pictures,
			Animation mirroredAnimation) {
		return new AnimationWithMirror(pictures, mirroredAnimation);
	}
}
