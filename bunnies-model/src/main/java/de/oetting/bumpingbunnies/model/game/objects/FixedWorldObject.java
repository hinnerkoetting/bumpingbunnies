package de.oetting.bumpingbunnies.model.game.objects;

public abstract class FixedWorldObject implements GameObjectWithImage {

	private int id;
	private final Rect rect;
	private int color;
	private ImageWrapper image;
	// Higher zIndex is drawn later (and draws over other objects)
	private int zIndex = -1;
	private boolean mirroredHorizontally;


	public FixedWorldObject(int id, long minX, long minY, long maxX, long maxY, int color) {
		this.id = id;
		this.rect = new Rect(minX, minY, maxX, maxY);
		this.color = color;
	}

	@Override
	public int getColor() {
		return this.color;
	}

	@Override
	public long minX() {
		return this.rect.getMinX();
	}

	@Override
	public long minY() {
		return this.rect.getMinY();
	}

	@Override
	public long maxX() {
		return this.rect.getMaxX();
	}

	@Override
	public long maxY() {
		return this.rect.getMaxY();
	}

	public int id() {
		return this.id;
	}

	@Override
	public void setBitmap(ImageWrapper b) {
		this.image = b;
	}

	@Override
	public ImageWrapper getBitmap() {
		return this.image;
	}

	public void applyImage(ImageWrapper extractImage) {
		this.image = extractImage;
	}

	@Override
	public void setCenterX(long gameX) {
		rect.setCenterX(gameX);
	}

	@Override
	public void setCenterY(long gameY) {
		rect.setCenterY(gameY);
	}

	public void setMinX(long minX) {
		rect.setMinX(minX);
	}

	public void setMaxX(long maxX) {
		rect.setMaxX(maxX);
	}

	public void setMinY(long minY) {
		rect.setMinY(minY);
	}

	public void setMaxY(long maxY) {
		rect.setMaxY(maxY);
	}

	@Override
	public String getImageKey() {
		if (image != null)
			return image.getImageKey();
		return null;
	}

	public int getZIndex() {
		if (zIndex == -1)
			throw new IllegalStateException("ZIndex was not set");
		return zIndex;
	}

	public void setZIndex(int zIndex) {
		this.zIndex = zIndex;
	}

	@Override
	public long getCenterX() {
		return (rect.getMaxX() + rect.getMinX()) / 2;
	}

	@Override
	public long getCenterY() {
		return (rect.getMaxY() + rect.getMinY()) / 2;
	}
	
	public void setColor(int color) {
		this.color = color;
	}

	public boolean isMirroredHorizontally() {
		return mirroredHorizontally;
	}

	public void setMirroredHorizontally(boolean mirroredHorizontally) {
		this.mirroredHorizontally = mirroredHorizontally;
	}
	
	

}
