package de.oetting.bumpingbunnies.usecases;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import de.oetting.bumpingbunnies.android.game.graphics.bitmapAltering.SimpleBitmapResizer;
import de.oetting.bumpingbunnies.core.game.graphics.PlayerImagesReader;
import de.oetting.bumpingbunnies.core.game.graphics.factory.PlayerImagesProvider;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class AndroidPlayerImagesProvier implements PlayerImagesProvider {

	private final PlayerImagesReader reader;
	private final SimpleBitmapResizer imageResizer;

	public AndroidPlayerImagesProvier(PlayerImagesReader reader) {
		this.reader = reader;
		this.imageResizer = new SimpleBitmapResizer();
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
			Bitmap bitmap = BitmapFactory.decodeStream(is);
			wrappers.add(new ImageWrapper(imageResizer.resize(bitmap, width, heigth), ""));
		}
		return wrappers;
	}

}
