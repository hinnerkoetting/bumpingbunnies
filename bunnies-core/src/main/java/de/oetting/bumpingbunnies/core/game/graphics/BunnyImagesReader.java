package de.oetting.bumpingbunnies.core.game.graphics;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.oetting.bumpingbunnies.model.game.objects.BunnyImageModel;

public class BunnyImagesReader {

	public List<InputStreamAndModel> loadAllRunningImages() {
		InputStreamAndModel image1 = new InputStreamAndModel(loadImage("v1d_run_1"), new BunnyImageModel(63, 60, 28, 100, 100));
		InputStreamAndModel image2 = new InputStreamAndModel(loadImage("v1d_run_2"), new BunnyImageModel(60, 59, 28, 100, 100));
		InputStreamAndModel image3 = new InputStreamAndModel(loadImage("v1d_run_3"), new BunnyImageModel(62, 59, 28, 100, 100));
		InputStreamAndModel image4 = new InputStreamAndModel(loadImage("v1d_run_4"), new BunnyImageModel(62, 58, 28, 100, 100));
		return Arrays.asList(image1, image2, image3, image4);
	}

	public List<InputStreamAndModel> loadAllFallingImages() {
		InputStreamAndModel image1 = new InputStreamAndModel(loadImage("v1d_down_1"), new BunnyImageModel(66, 61, 28, 100, 100));
		InputStreamAndModel image2 = new InputStreamAndModel(loadImage("v1d_down_2"), new BunnyImageModel(66, 61, 28, 100, 100));
		InputStreamAndModel image3 = new InputStreamAndModel(loadImage("v1d_down_3"), new BunnyImageModel(67, 62, 28, 100, 100));
		InputStreamAndModel image4 = new InputStreamAndModel(loadImage("v1d_down_4"), new BunnyImageModel(67, 62, 28, 100, 100));
		return Arrays.asList(image1, image2, image3, image4);
	}

	public List<InputStreamAndModel> loadAllJumpingImages() {
		InputStreamAndModel image1 = new InputStreamAndModel(loadImage("v1d_down_1"), new BunnyImageModel(68, 54, 28, 100, 100));
		InputStreamAndModel image2 = new InputStreamAndModel(loadImage("v1d_down_2"), new BunnyImageModel(68, 64, 28, 100, 100));
		InputStreamAndModel image3 = new InputStreamAndModel(loadImage("v1d_down_3"), new BunnyImageModel(68, 65, 28, 100, 100));
		InputStreamAndModel image4 = new InputStreamAndModel(loadImage("v1d_down_4"), new BunnyImageModel(68, 55, 28, 100, 100));
		return Arrays.asList(image1, image2, image3, image4);
	}

	public List<InputStreamAndModel> loadAllSittingImages() {
		InputStreamAndModel image1 = new InputStreamAndModel(loadImage("v1d_sit_1"), new BunnyImageModel(55, 63, 28, 100, 100));
		InputStreamAndModel image2 = new InputStreamAndModel(loadImage("v1d_sit_2"), new BunnyImageModel(55, 63, 28, 100, 100));
		InputStreamAndModel image3 = new InputStreamAndModel(loadImage("v1d_sit_3"), new BunnyImageModel(55, 63, 28, 100, 100));
		InputStreamAndModel image4 = new InputStreamAndModel(loadImage("v1d_sit_4"), new BunnyImageModel(55, 63, 28, 100, 100));
		return Arrays.asList(image1, image2, image3, image4);
	}

	public List<InputStreamAndModel> loadAllJumpingUpImages() {
		return loadImages("v1d_run_1");
	}

	private List<InputStreamAndModel> loadImages(String... names) {
		List<InputStreamAndModel> streams = new ArrayList<InputStreamAndModel>(names.length);
		for (String name : names) {
			streams.add(new InputStreamAndModel(loadImage(name), new BunnyImageModel(66, 61, 28, 100, 100)));
		}
		return streams;
	}

	public InputStream loadOneImage() {
		return loadImage("v1d_run_1");
	}

	public InputStream loadImage(String name) {
		InputStream is = loadImageOrNull(name);
		if (is == null)
			throw new IllegalArgumentException("Could not find resource: " + name);
		return is;
	}
	public InputStream loadImageOrNull(String name) {
		String resource = "/" + name + ".png";
		return getClass().getResourceAsStream(resource);
	}

}
