package de.oetting.bumpingbunnies.worldcreator.load;

import javax.imageio.ImageIO;

import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class SwingResourceProvider implements ResourceProvider {

	private final FilesystemImageReader imageReader;

	public SwingResourceProvider(FilesystemImageReader imageReader) {
		this.imageReader = imageReader;
	}

	@Override
	public ImageWrapper readBitmap(String fileName) {
		try {
			return new ImageWrapper(ImageIO.read(imageReader.readAsStream(fileName)), fileName);
		} catch (Exception e) {
			throw new RuntimeException("Could not read image with name " + fileName, e);
		}
	}

}
