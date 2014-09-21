package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import android.graphics.Bitmap;
import de.oetting.bumpingbunnies.core.game.graphics.AnimationImpl;
import de.oetting.bumpingbunnies.core.graphics.ImageResizer;
import de.oetting.bumpingbunnies.tests.IntegrationTests;
import de.oetting.bumpingbunnies.usecases.game.model.Image;

@Category(IntegrationTests.class)
@RunWith(RobolectricTestRunner.class)
@Config(emulateSdk = 18)
public class AnimationImplTest {

	@Mock
	private ImageResizer resizer;

	@Test
	public void test() {
		createAnimation(1, 1);
	}

	private void createAnimation(int numberOfPictures, int timeBetweenPicture) {
		List<Image> pictures = createNumberOfPictures(numberOfPictures);
		new AnimationImpl(pictures, timeBetweenPicture, this.resizer);
	}

	private List<Image> createNumberOfPictures(int number) {
		List<Image> bitmaps = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			Image bitmap = new AndroidImage(Bitmap.createBitmap(0, 0, null));
			bitmaps.add(bitmap);
		}
		return bitmaps;
	}

}
