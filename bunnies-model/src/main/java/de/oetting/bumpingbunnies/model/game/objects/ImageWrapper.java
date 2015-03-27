package de.oetting.bumpingbunnies.model.game.objects;

public class ImageWrapper implements Comparable<ImageWrapper> {

	private final Object image;
	/**
	 * Reference of the image, which is used in levels.
	 */
	private final String key;

	public ImageWrapper(Object image, String key) {
		this.image = image;
		this.key = key;
	}

	public Object getBitmap() {
		return image;
	}

	public String getImageKey() {
		return key;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) 
			return false;
		if ((obj instanceof ImageWrapper)) 
			return false;
		return ((ImageWrapper) obj).getImageKey().equals(key);
	}
	
	@Override
	public int compareTo(ImageWrapper o) {
		return key.compareTo(o.key);
	}
	
	@Override
	public int hashCode() {
		return key.hashCode();
	}
}
