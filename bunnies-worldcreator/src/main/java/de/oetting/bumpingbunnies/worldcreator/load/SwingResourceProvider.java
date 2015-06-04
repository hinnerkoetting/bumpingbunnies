package de.oetting.bumpingbunnies.worldcreator.load;

import java.io.IOException;

import javax.imageio.ImageIO;

import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class SwingResourceProvider implements ResourceProvider {

	private final ClasspathImageReader imageReader;

	public SwingResourceProvider(ClasspathImageReader imageReader) {
		this.imageReader = imageReader;
	}

	@Override
	public ImageWrapper readBitmap(String fileName) {
		try {
			return new ImageWrapper(ImageIO.read(imageReader.readAsStream(fileName)), fileName);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}