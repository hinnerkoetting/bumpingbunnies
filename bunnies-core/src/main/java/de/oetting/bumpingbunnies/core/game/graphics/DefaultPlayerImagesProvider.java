package de.oetting.bumpingbunnies.core.game.graphics;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.ImagesColorer;
import de.oetting.bumpingbunnies.core.game.graphics.factory.PlayerImagesProvider;
import de.oetting.bumpingbunnies.core.game.graphics.factory.StreamImageLoader;
import de.oetting.bumpingbunnies.model.game.objects.BunnyImage;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class DefaultPlayerImagesProvider implements PlayerImagesProvider {

	private final BunnyImagesReader reader;
	private final StreamImageLoader imageLoader;

	public DefaultPlayerImagesProvider(BunnyImagesReader reader, StreamImageLoader imageLoader) {
		this.reader = reader;
		this.imageLoader = imageLoader;
	}

	public List<BunnyImage> loadAllRunningImages(int width, int heigth) {
		return load(reader.loadAllRunningImages(), width, heigth);
	}

	public List<BunnyImage> loadAllFallingImages(int width, int heigth) {
		return load(reader.loadAllFallingImages(), width, heigth);
	}

	public List<BunnyImage> loadAllJumpingImages(int width, int heigth) {
		return load(reader.loadAllJumpingImages(), width, heigth);
	}

	public List<BunnyImage> loadAllSittingImages(int width, int heigth) {
		return load(reader.loadAllSittingImages(), width, heigth);
	}

	public List<BunnyImage> loadAllJumpingUpImages(int width, int heigth) {
		return load(reader.loadAllJumpingUpImages(), width, heigth);
	}

	private List<BunnyImage> load(List<InputStreamAndModel> images, int width, int heigth) {
		List<BunnyImage> wrappers = new ArrayList<BunnyImage>(images.size());
		for (InputStreamAndModel input : images) {
			wrappers.add(new BunnyImage(loadOneImage(input.getInputStream(), width, heigth), input.getModel()));
		}
		return wrappers;
	}

	public ImageWrapper loadOneImage(int width, int heigth) {
		return loadOneImage(reader.loadOneImage(), width, heigth);
	}

	private ImageWrapper loadOneImage(InputStream is, int width, int height) {
		return imageLoader.loadImage(is, width, height);
	}

	public ImageWrapper readSiggi() {
		return new ImageWrapper(new Image(reader.loadSiggi()), "");
	}
}
