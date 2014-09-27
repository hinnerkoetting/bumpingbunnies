package de.oetting.bumpingbunnies.pc.graphics.drawables.factory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import de.oetting.bumpingbunnies.core.game.graphics.PlayerImagesReader;
import de.oetting.bumpingbunnies.core.game.graphics.factory.PlayerImagesProvider;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;

public class PcPlayerImagesProvider implements PlayerImagesProvider {

	private final PlayerImagesReader reader;

	public PcPlayerImagesProvider(PlayerImagesReader reader) {
		this.reader = reader;
	}

	@Override
	public List<ImageWrapper> loadAllRunningImages(int width, int heigth) {
		return load(reader.loadAllRunningImages(), width, heigth);
	}

	@Override
	public List<ImageWrapper> loadAllFallingImages(int width, int heigth) {
		return load(reader.loadAllFallingImages(), width, heigth);
	}

	@Override
	public List<ImageWrapper> loadAllJumpingImages(int width, int heigth) {
		return load(reader.loadAllJumpingImages(), width, heigth);
	}

	@Override
	public List<ImageWrapper> loadAllSittingImages(int width, int heigth) {
		return load(reader.loadAllSittingImages(), width, heigth);
	}

	@Override
	public List<ImageWrapper> loadAllJumpingUpImages(int width, int heigth) {
		return load(reader.loadAllJumpingUpImages(), width, heigth);
	}

	private List<ImageWrapper> load(List<InputStream> images, int width, int heigth) {
		List<ImageWrapper> wrappers = new ArrayList<ImageWrapper>(images.size());
		for (InputStream is : images) {
			wrappers.add(new ImageWrapper(new Image(is, width, heigth, false, true)));
		}
		return wrappers;
	}

}
