package de.oetting.bumpingbunnies.core.game;

import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ImagesZipLoader {

	public ImageCache loadAllImages(InputStream worldZip, ImageCreator imageCreator) {
		try {
			ImageCache images = new ImageCache();
			ZipInputStream zis = new ZipInputStream(worldZip);
			ZipEntry entry = zis.getNextEntry();
			while (entry != null) {
				if (entry.getName().endsWith("png"))
					images.addImage(imageCreator.createImage(zis, entry.getName().split("\\.")[0]));
				entry = zis.getNextEntry();
			}
			worldZip.close();
			return images;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
