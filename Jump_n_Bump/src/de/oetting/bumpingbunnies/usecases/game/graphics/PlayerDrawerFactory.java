package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerDrawerFactory {

	public static PlayerDrawer create(Player player, Resources resources) {

		AnimationWithMirror animation = AnimationWithMirrorFactory.create(
				createRunningAnimation(resources, player), 100);
		AnimationWithMirror fallingAnimation = AnimationWithMirrorFactory.create(
				createFallingAnimation(resources, player), 100);
		AnimationWithMirror jumpingAnimation = AnimationWithMirrorFactory.create(
				createJumpingAnimation(resources, player), 100);
		AnimationWithMirror sittingAnimation = AnimationWithMirrorFactory.create(
				createSittingAnimation(resources, player), 100);
		return new PlayerDrawer(player, animation, fallingAnimation, jumpingAnimation, sittingAnimation);
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

	// private static List<Bitmap> createListOfTestBitmap(Resources resources,
	// Player player) {
	//
	// Bitmap bitmap = loadBitmap(resources, R.drawable.bunny_v6g);
	// Bitmap convertedColor = GrayScaleToColorConverter.convertToColor(
	// bitmap, player.getColor());
	// return Arrays.asList(convertedColor);
	// }

	// private static List<Bitmap> createListOfAllBitmaps(Resources resources) {
	// return Arrays.asList(
	// loadBitmap(resources, R.drawable.v4_pink_4steps_000001),
	// loadBitmap(resources, R.drawable.v4_pink_4steps_000002),
	// loadBitmap(resources, R.drawable.v4_pink_4steps_000003),
	// loadBitmap(resources, R.drawable.v4_pink_4steps_000004),
	// loadBitmap(resources, R.drawable.v4_pink_4steps_000005),
	// loadBitmap(resources, R.drawable.v4_pink_4steps_000006),
	// loadBitmap(resources, R.drawable.v4_pink_4steps_000007),
	// loadBitmap(resources, R.drawable.v4_pink_4steps_000008),
	// loadBitmap(resources, R.drawable.v4_pink_4steps_000009),
	// loadBitmap(resources, R.drawable.v4_pink_4steps_000010),
	// loadBitmap(resources, R.drawable.v4_pink_4steps_000011),
	// loadBitmap(resources, R.drawable.v4_pink_4steps_000012));
	// }

	private static Bitmap loadBitmap(Resources resources, int id) {
		return BitmapFactory.decodeResource(resources, id);
	}
}
