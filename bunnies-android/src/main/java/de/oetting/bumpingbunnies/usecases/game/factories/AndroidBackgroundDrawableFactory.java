package de.oetting.bumpingbunnies.usecases.game.factories;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import de.oetting.bumpingbunnies.android.game.graphics.bitmapAltering.SimpleBitmapResizer;
import de.oetting.bumpingbunnies.core.game.BackgroundReader;
import de.oetting.bumpingbunnies.core.game.graphics.BackgroundDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.factory.BackgroundDrawableFactory;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class AndroidBackgroundDrawableFactory implements BackgroundDrawableFactory {

	private final boolean drawBackground;

	public AndroidBackgroundDrawableFactory(boolean drawBackground) {
		this.drawBackground = drawBackground;
	}

	@Override
	public BackgroundDrawer create(int screenWidth, int screenHeight) {
		Bitmap background = BitmapFactory.decodeStream(new BackgroundReader().readBackground());
		Bitmap resizedImage = new SimpleBitmapResizer().resize(background, screenWidth, screenHeight);
		return new BackgroundDrawer(new ImageWrapper(resizedImage, ""), this.drawBackground);
	}
}
