package de.oetting.bumpingbunnies.model.game.objects;

public class NullObject implements GameObjectWithImage {

	@Override
	public long maxX() {
		return 0;
	}

	@Override
	public long maxY() {
		return 0;
	}

	@Override
	public long minX() {
		return 0;
	}

	@Override
	public long minY() {
		return 0;
	}

	@Override
	public int accelerationOnThisGround() {
		return 0;
	}

	@Override
	public void setCenterX(long gameX) {
	}

	@Override
	public void setCenterY(long gameY) {
	}

	@Override
	public long getCenterX() {
		return 0;
	}

	@Override
	public long getCenterY() {
		return 0;
	}

	@Override
	public void setMinY(long newBottomY) {
	}

	@Override
	public void setMinX(long newLeft) {
	}

	@Override
	public int id() {
		return 0;
	}

	@Override
	public void setMaxX(long newRight) {
	}

	@Override
	public void setMaxY(long newTopY) {
	}

	@Override
	public int getColor() {
		return 0;
	}

	@Override
	public void setColor(int color) {
	}

	@Override
	public ImageWrapper getBitmap() {
		return null;
	}

	@Override
	public void setBitmap(ImageWrapper b) {
	}

	@Override
	public String getImageKey() {
		return null;
	}

	@Override
	public void applyImage(ImageWrapper wrapper) {
	}

	@Override
	public int getZIndex() {
		return 0;
	}

	@Override
	public void setZIndex(int index) {
	}

	@Override
	public boolean isMirroredHorizontally() {
		return false;
	}

	@Override
	public void setMirroredHorizontally(boolean value) {
	}

}
