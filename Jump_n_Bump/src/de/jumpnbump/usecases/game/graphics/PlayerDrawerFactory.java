package de.jumpnbump.usecases.game.graphics;

import java.util.Arrays;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import de.jumpnbump.R;
import de.jumpnbump.usecases.game.model.Player;

public class PlayerDrawerFactory {

	public static PlayerDrawer create(Player player, Resources resources) {
		AnimationWithMirror animation = AnimationWithMirrorFactory.create(
				createListOfTestBitmap(resources), 20);
		return new PlayerDrawer(player, animation);
	}

	private static Animation createAnimation(Resources resources) {
		List<Bitmap> bitmaps = createListOfAllBitmaps(resources);
		return new NormalAnimation(bitmaps, 20);
	}

	private static List<Bitmap> createListOfTestBitmap(Resources resources) {
		return Arrays.asList(loadBitmap(resources,
				R.drawable.frame_000001_000001));
	}

	private static List<Bitmap> createListOfAllBitmaps(Resources resources) {
		return Arrays.asList(
				loadBitmap(resources, R.drawable.v4_pink_4steps_000001),
				loadBitmap(resources, R.drawable.v4_pink_4steps_000002),
				loadBitmap(resources, R.drawable.v4_pink_4steps_000003),
				loadBitmap(resources, R.drawable.v4_pink_4steps_000004),
				loadBitmap(resources, R.drawable.v4_pink_4steps_000005),
				loadBitmap(resources, R.drawable.v4_pink_4steps_000006),
				loadBitmap(resources, R.drawable.v4_pink_4steps_000007),
				loadBitmap(resources, R.drawable.v4_pink_4steps_000008),
				loadBitmap(resources, R.drawable.v4_pink_4steps_000009),
				loadBitmap(resources, R.drawable.v4_pink_4steps_000010),
				loadBitmap(resources, R.drawable.v4_pink_4steps_000011),
				loadBitmap(resources, R.drawable.v4_pink_4steps_000012));
	}

	private static Bitmap loadBitmap(Resources resources, int id) {
		return BitmapFactory.decodeResource(resources, id);
	}
}
