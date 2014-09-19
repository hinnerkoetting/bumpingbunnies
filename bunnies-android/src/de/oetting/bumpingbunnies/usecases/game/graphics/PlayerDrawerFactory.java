package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerDrawerFactory {

	public static PlayerDrawer create(Player player, Resources resources) {

		ConditionalMirroredAnimation runningAnimation = AnimationWithMirrorFactory.createRunningAnimation(player,
				createRunningAnimation(resources, player), 100);
		ConditionalMirroredAnimation fallingAnimation = AnimationWithMirrorFactory.createFallingAnimation(player,
				createFallingAnimation(resources, player), 100);
		ConditionalMirroredAnimation jumpingAnimation = AnimationWithMirrorFactory.createJumpingAnimation(player,
				createJumpingAnimation(resources, player), 100);
		ConditionalMirroredAnimation sittingAnimation = AnimationWithMirrorFactory.createSittingAnimation(player,
				createSittingAnimation(resources, player), 100);
		ConditionalMirroredAnimation jumpingOnlyUpAnimation = AnimationWithMirrorFactory.createJumpingOnlyUpAnimation(player,
				createJumpingOnlyUpAnimation(resources, player), 100);
		List<ConditionalMirroredAnimation> animations = Arrays.asList(runningAnimation, fallingAnimation, jumpingAnimation,
				sittingAnimation, jumpingOnlyUpAnimation);
		return new PlayerDrawer(player, animations);
	}

	private static List<Bitmap> createRunningAnimation(Resources resources, Player player) {
		return loadColoredImages(resources, player, R.drawable.v1d_run_1, R.drawable.v1d_run_2, R.drawable.v1d_run_3, R.drawable.v1d_run_4);
	}

	private static List<Bitmap> createFallingAnimation(Resources resources, Player player) {
		return loadColoredImages(resources, player, R.drawable.v1d_down_1, R.drawable.v1d_down_2, R.drawable.v1d_down_3,
				R.drawable.v1d_down_4);
	}

	private static List<Bitmap> createJumpingAnimation(Resources resources, Player player) {
		return loadColoredImages(resources, player, R.drawable.v1d_up_1, R.drawable.v1d_up_2, R.drawable.v1d_up_3,
				R.drawable.v1d_up_4);
	}

	private static List<Bitmap> createSittingAnimation(Resources resources, Player player) {
		return loadColoredImages(resources, player, R.drawable.v1d_sit_1, R.drawable.v1d_sit_2, R.drawable.v1d_sit_3,
				R.drawable.v1d_sit_4);
	}

	private static List<Bitmap> createJumpingOnlyUpAnimation(Resources resources, Player player) {
		return loadColoredImages(resources, player, R.drawable.v1d_run_1);
	}

	private static List<Bitmap> loadColoredImages(Resources resources, Player player, int... ids) {
		List<Bitmap> originalBitmaps = new ArrayList<Bitmap>(ids.length);
		for (int id : ids) {
			originalBitmaps.add(loadBitmap(resources, id));
		}
		return colorImages(originalBitmaps, player);
	}

	private static List<Bitmap> colorImages(List<Bitmap> originalBitmaps, Player player) {
		List<Bitmap> coloredBitmaps = new ArrayList<Bitmap>(originalBitmaps.size());
		for (Bitmap bitmap : originalBitmaps) {
			Bitmap coloredBitmap = GrayScaleToColorConverter.convertToColor(
					bitmap, player.getColor());
			coloredBitmaps.add(coloredBitmap);
		}
		return coloredBitmaps;
	}

	private static Bitmap loadBitmap(Resources resources, int id) {
		return BitmapFactory.decodeResource(resources, id);
	}
}
