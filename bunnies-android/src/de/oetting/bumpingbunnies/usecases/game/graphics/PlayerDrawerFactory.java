package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.game.graphics.bitmapAltering.MirrorBitmapResizer;
import de.oetting.bumpingbunnies.android.game.graphics.bitmapAltering.SimpleBitmapResizer;
import de.oetting.bumpingbunnies.core.game.graphics.AnimationWithMirrorFactory;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.game.graphics.ConditionalMirroredAnimation;
import de.oetting.bumpingbunnies.core.game.graphics.PlayerDrawer;
import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerDrawerFactory {

	public static PlayerDrawer create(Player player, Resources resources, CanvasDelegate canvas) {
		ImageResizer imageWrapperResizer = new SimpleBitmapResizer();
		ImageResizer mirrorImageWrapperResizer = new MirrorBitmapResizer();

		ConditionalMirroredAnimation runningAnimation = AnimationWithMirrorFactory.createRunningAnimation(player, createRunningAnimation(resources, player),
				100, imageWrapperResizer, mirrorImageWrapperResizer, canvas);
		ConditionalMirroredAnimation fallingAnimation = AnimationWithMirrorFactory.createFallingAnimation(player, createFallingAnimation(resources, player),
				100, imageWrapperResizer, mirrorImageWrapperResizer, canvas);
		ConditionalMirroredAnimation jumpingAnimation = AnimationWithMirrorFactory.createJumpingAnimation(player, createJumpingAnimation(resources, player),
				100, imageWrapperResizer, mirrorImageWrapperResizer, canvas);
		ConditionalMirroredAnimation sittingAnimation = AnimationWithMirrorFactory.createSittingAnimation(player, createSittingAnimation(resources, player),
				100, imageWrapperResizer, mirrorImageWrapperResizer, canvas);
		ConditionalMirroredAnimation jumpingOnlyUpAnimation = AnimationWithMirrorFactory.createJumpingOnlyUpAnimation(player,
				createJumpingOnlyUpAnimation(resources, player), 100, imageWrapperResizer, mirrorImageWrapperResizer, canvas);
		List<ConditionalMirroredAnimation> animations = Arrays.asList(runningAnimation, fallingAnimation, jumpingAnimation, sittingAnimation,
				jumpingOnlyUpAnimation);
		return new PlayerDrawer(player, animations);
	}

	private static List<ImageWrapper> createRunningAnimation(Resources resources, Player player) {
		return loadColoredImageWrappers(resources, player, R.drawable.v1d_run_1, R.drawable.v1d_run_2, R.drawable.v1d_run_3, R.drawable.v1d_run_4);
	}

	private static List<ImageWrapper> createFallingAnimation(Resources resources, Player player) {
		return loadColoredImageWrappers(resources, player, R.drawable.v1d_down_1, R.drawable.v1d_down_2, R.drawable.v1d_down_3, R.drawable.v1d_down_4);
	}

	private static List<ImageWrapper> createJumpingAnimation(Resources resources, Player player) {
		return loadColoredImageWrappers(resources, player, R.drawable.v1d_up_1, R.drawable.v1d_up_2, R.drawable.v1d_up_3, R.drawable.v1d_up_4);
	}

	private static List<ImageWrapper> createSittingAnimation(Resources resources, Player player) {
		return loadColoredImageWrappers(resources, player, R.drawable.v1d_sit_1, R.drawable.v1d_sit_2, R.drawable.v1d_sit_3, R.drawable.v1d_sit_4);
	}

	private static List<ImageWrapper> createJumpingOnlyUpAnimation(Resources resources, Player player) {
		return loadColoredImageWrappers(resources, player, R.drawable.v1d_run_1);
	}

	private static List<ImageWrapper> loadColoredImageWrappers(Resources resources, Player player, int... ids) {
		List<Bitmap> originalBitmaps = new ArrayList<Bitmap>(ids.length);
		for (int id : ids) {
			originalBitmaps.add(loadBitmap(resources, id));
		}
		return colorImageWrappers(originalBitmaps, player);
	}

	private static List<ImageWrapper> colorImageWrappers(List<Bitmap> originalBitmaps, Player player) {
		List<ImageWrapper> coloredBitmaps = new ArrayList<ImageWrapper>(originalBitmaps.size());
		for (Bitmap bitmap : originalBitmaps) {
			Bitmap coloredBitmap = GrayScaleToColorConverter.convertToColor(bitmap, player.getColor());
			coloredBitmaps.add(new ImageWrapper(coloredBitmap));
		}
		return coloredBitmaps;
	}

	private static Bitmap loadBitmap(Resources resources, int id) {
		return BitmapFactory.decodeResource(resources, id);
	}
}
