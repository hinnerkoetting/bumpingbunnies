package de.oetting.bumpingbunnies.core.worldCreation;

import java.io.InputStream;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class ClasspathImageReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(ClasspathImageReader.class);

	public InputStream readAsStream(String fileName) {
		String classpathName = "/drawable/" + fileName + addEndingIfNecessary(fileName);
		InputStream resourceAsStream = getClass().getResourceAsStream(classpathName);
		if (resourceAsStream == null) {
			LOGGER.warn("Could not read " + fileName);
			return getClass().getResourceAsStream("/drawable/error.png");
		}
		return resourceAsStream;
	}

	private String addEndingIfNecessary(String fileName) {
		return fileName.endsWith(".png") ? "" : ".png";
	}
}
