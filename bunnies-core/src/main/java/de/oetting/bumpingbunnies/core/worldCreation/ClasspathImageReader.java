package de.oetting.bumpingbunnies.core.worldCreation;

import java.io.InputStream;

public class ClasspathImageReader {

	public InputStream readAsStream(String fileName) {
		String classpathName = "/drawable/" + fileName + ".png";
		InputStream resourceAsStream = getClass().getResourceAsStream(classpathName);
		if (resourceAsStream == null)
			throw new IllegalArgumentException("Could not find file in classpath: " + classpathName);
		return resourceAsStream;
	}

}
