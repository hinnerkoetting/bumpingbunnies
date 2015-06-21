package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import java.io.InputStream;

import javafx.scene.image.Image;
import de.oetting.bumpingbunnies.core.game.graphics.factory.StreamImageLoader;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class PcImageLoader implements StreamImageLoader {

	@Override
	public ImageWrapper loadImage(InputStream inputStream, int width, int height) {
		return new ImageWrapper(new Image(inputStream, width, height, false, true), "");
	}

	@Override
	public ImageWrapper loadImage(InputStream inputStream) {
		return new ImageWrapper(new Image(inputStream), "");
	}

}
