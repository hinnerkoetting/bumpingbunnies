package de.oetting.bumpingbunnies.core.game.graphics;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class PlayerImagesReader {

	public List<InputStream> loadAllRunningImages() {
		return loadImages("v1d_run_1", "v1d_run_2", "v1d_run_3", "v1d_run_4");
	}

	public List<InputStream> loadAllFallingImages() {
		return loadImages("v1d_down_1", "v1d_down_2", "v1d_down_3", "v1d_down_4");
	}

	public List<InputStream> loadAllJumpingImages() {
		return loadImages("v1d_up_1", "v1d_up_2", "v1d_up_3", "v1d_up_4");
	}

	public List<InputStream> loadAllSittingImages() {
		return loadImages("v1d_sit_1", "v1d_sit_2", "v1d_sit_3", "v1d_sit_4");
	}

	public List<InputStream> loadAllJumpingUpImages() {
		return loadImages("v1d_run_1");
	}

	private List<InputStream> loadImages(String... names) {
		List<InputStream> streams = new ArrayList<InputStream>(names.length);
		for (String name : names) {
			streams.add(loadImage(name));
		}
		return streams;
	}

	public InputStream loadOneImage() {
		return loadImage("v1d_run_1");
	}

	private InputStream loadImage(String name) {
		String resource = "/drawable/" + name + ".png";
		InputStream is = getClass().getResourceAsStream(resource);
		if (is == null)
			throw new IllegalArgumentException("Could not find resource: " + resource);
		return is;
	}
}
