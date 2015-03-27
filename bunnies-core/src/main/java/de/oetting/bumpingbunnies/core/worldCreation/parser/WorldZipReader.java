package de.oetting.bumpingbunnies.core.worldCreation.parser;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import de.oetting.bumpingbunnies.core.world.WorldConstants;

public class WorldZipReader {

	public InputStream findWorldXml(ZipInputStream zipInputStream) {
		try {
			ZipEntry entry = zipInputStream.getNextEntry();
			while (entry != null) {
				if (entry.getName().equals(WorldConstants.WORLD_FILE_NAME)) {
					return zipInputStream;
				}
				entry = zipInputStream.getNextEntry();
			}
			throw new IllegalArgumentException("no world exists");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
