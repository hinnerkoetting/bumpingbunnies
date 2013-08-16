package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import android.graphics.Bitmap;

import com.xtremelabs.robolectric.RobolectricTestRunner;

import de.oetting.bumpingbunnies.usecases.game.graphics.bitmapAltering.BitmapResizer;

@RunWith(RobolectricTestRunner.class)
public class AnimationImplTest {

	private AnimationImpl fixture;
	@Mock
	private BitmapResizer resizer;

	@Test
	public void test() {
		createAnimation(1, 1);
	}

	private void createAnimation(int numberOfPictures, int timeBetweenPicture) {
		List<Bitmap> pictures = createNumberOfPictures(numberOfPictures);
		this.fixture = new AnimationImpl(pictures, timeBetweenPicture, this.resizer);
	}

	private List<Bitmap> createNumberOfPictures(int number) {
		List<Bitmap> bitmaps = new ArrayList<>();
		for (int i = 0; i < number; i++) {
			Bitmap bitmap = Bitmap.createBitmap(0, 0, null);
			bitmaps.add(bitmap);
		}
		return bitmaps;
	}

}
