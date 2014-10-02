package de.oetting.bumpingbunnies.usecases.game.factories;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.game.graphics.bitmapAltering.SimpleBitmapResizer;
import de.oetting.bumpingbunnies.core.game.graphics.BackgroundDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.factory.BackgroundDrawableFactory;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class AndroidBackgroundDrawableFactory implements BackgroundDrawableFactory {

	private final Resources resources;
	private final boolean drawBackground;

	public AndroidBackgroundDrawableFactory(Resources resources, boolean drawBackground) {
		this.resources = resources;
		this.drawBackground = drawBackground;
	}

	@Override
	public BackgroundDrawer create(int screenWidth, int screenHeight) {
		Bitmap background = BitmapFactory.decodeResource(this.resources, R.drawable.hintergrund2);
		Bitmap resizedImage = new SimpleBitmapResizer().resize(background, screenWidth, screenHeight);
		return new BackgroundDrawer(new ImageWrapper(resizedImage), this.drawBackground);
	}

}
