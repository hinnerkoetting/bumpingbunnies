package de.oetting.bumpingbunnies.worldcreator.load;

import java.io.FileInputStream;
import java.io.IOError;
import java.io.IOException;
import java.io.InputStream;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class FilesystemImageReader {

	private static final Logger LOGGER = LoggerFactory.getLogger(FilesystemImageReader.class);

	private final String sourceDirectory;

	public FilesystemImageReader(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory;
	}

	public InputStream readAsStream(String fileName) {
		try {
			String completeFilename = "/" + fileName + addEndingIfNecessary(fileName);
			InputStream resourceAsStream = new FileInputStream(sourceDirectory + completeFilename);
			return resourceAsStream;
		} catch (IOException e) {
			LOGGER.warn("Could not read " + fileName + " I tried to find it in the filesystem under "
					+ sourceDirectory + fileName);
			return getClass().getResourceAsStream("/error.png");
		}
	}

	private String addEndingIfNecessary(String fileName) {
		return fileName.endsWith(".png") ? "" : ".png";
	}
}
