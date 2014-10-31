package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import javafx.scene.image.Image;
import de.oetting.bumpingbunnies.core.game.BackgroundReader;
import de.oetting.bumpingbunnies.core.game.graphics.BackgroundDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.factory.BackgroundDrawableFactory;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class PcBackgroundDrawableFactory implements BackgroundDrawableFactory {

	@Override
	public BackgroundDrawer create(int screenWidth, int screenHeight) {
		Image image = new Image(new BackgroundReader().readBackground(), screenWidth, screenHeight, false, true);
		return new BackgroundDrawer(new ImageWrapper(image, ""), true);
	}

}
