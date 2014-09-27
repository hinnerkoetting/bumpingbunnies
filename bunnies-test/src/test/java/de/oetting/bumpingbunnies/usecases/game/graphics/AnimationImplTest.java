package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.graphics.Bitmap;
import de.oetting.bumpingbunnies.core.game.graphics.AnimationImpl;
import de.oetting.bumpingbunnies.tests.IntegrationTests;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class AnimationImplTest {

	@Test
	public void test() {
		createAnimation(1, 1);
	}

	private void createAnimation(int numberOfPictures, int timeBetweenPicture) {
		List<ImageWrapper> pictures = createNumberOfPictures(numberOfPictures);
		new AnimationImpl(pictures, timeBetweenPicture);
	}

	private List<ImageWrapper> createNumberOfPictures(int number) {
		List<ImageWrapper> bitmaps = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			ImageWrapper bitmap = new ImageWrapper(Bitmap.createBitmap(0, 0, null));
			bitmaps.add(bitmap);
		}
		return bitmaps;
	}

}
