package de.jumpnbump.usecases.viewer.xml;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.ImageWrapper;

public class LevelStorer {

	private final XmlStorer xmlStorer;

	public LevelStorer(XmlStorer xmlStorer) {
		this.xmlStorer = xmlStorer;
	}

	public void storeLevel(File file, World world) {
		ZipOutputStream os = createFile(file);
		writeWorld(os);
		writeImages(os, world);
		close(os);
	}

	private void close(ZipOutputStream os) {
		try {
			os.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private ZipOutputStream createFile(File file) {
		try {
			ZipOutputStream os = new ZipOutputStream(new FileOutputStream(file));
			return os;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeWorld(ZipOutputStream os) {
		try {
			os.putNextEntry(new ZipEntry("world.xml"));
			xmlStorer.saveXml(os);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void writeImages(ZipOutputStream os, World world) {
		Set<ImageWrapper> images = extractDifferentImages(world);
		for (ImageWrapper image : images)
			write(os, image);
	}

	private void write(ZipOutputStream os, ImageWrapper image) {
		try {
			os.putNextEntry(new ZipEntry(image.getImageKey() + ".png"));
			ImageIO.write((RenderedImage) image.getBitmap(), "png", os);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private Set<ImageWrapper> extractDifferentImages(World world) {
		Set<ImageWrapper> images = new TreeSet<>();
		for (GameObjectWithImage imageObject : world.getAllDrawingObjects()) {
			if (imageObject.getBitmap() != null)
				images.add(imageObject.getBitmap());
		}
		return images;
	}

}
