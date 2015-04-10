package de.oetting.bumpingbunnies.usecases;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import de.oetting.bumpingbunnies.android.game.graphics.bitmapAltering.SimpleBitmapResizer;
import de.oetting.bumpingbunnies.core.game.graphics.BunnyImagesReader;
import de.oetting.bumpingbunnies.core.game.graphics.factory.PlayerImagesProvider;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class AndroidPlayerImagesProvider implements PlayerImagesProvider {

	private final BunnyImagesReader reader;
	private final SimpleBitmapResizer imageResizer;

	public AndroidPlayerImagesProvider(BunnyImagesReader reader) {
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
	@Override
	public ImageWrapper loadOneImage(int width, int heigth) {
		return load(Collections.singletonList(reader.loadOneImage()), width, heigth).get(0);
	}

	private List<ImageWrapper> load(List<InputStream> images, int width, int heigth) {
		List<ImageWrapper> wrappers = new ArrayList<ImageWrapper>(images.size());
		for (InputStream is : images) {
			wrappers.add(loadOneImage(width, heigth, is));
		}
		return wrappers;
	}

	private ImageWrapper loadOneImage(int width, int heigth,
			 InputStream is) {
		Bitmap bitmap = BitmapFactory.decodeStream(is);
		return new ImageWrapper(imageResizer.resize(bitmap, width, heigth), "");
	}

}
