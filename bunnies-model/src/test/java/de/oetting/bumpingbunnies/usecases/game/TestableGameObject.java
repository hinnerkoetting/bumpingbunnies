package de.oetting.bumpingbunnies.usecases.game;

import de.oetting.bumpingbunnies.model.game.objects.GameObject;

public class TestableGameObject implements GameObject {

	public long maxX;
	public long maxY;
	public long minX;
	public long minY;
	public int color;
	public int accelerationOnThisGround;

	
	public TestableGameObject() {
	}
	

	public TestableGameObject(long minX, long minY, long maxX, long maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
		this.minX = minX;
		this.minY = minY;
	}


	@Override
	public long maxX() {
		return this.maxX;
	}

	@Override
	public long maxY() {
		return this.maxY;
	}

	@Override
	public long minX() {
		return this.minX;
	}

	@Override
	public long minY() {
		return this.minY;
	}

	@Override
	public int accelerationOnThisGround() {
		return this.accelerationOnThisGround;
	}

	@Override
	public void setCenterX(long gameX) {
		throw new IllegalArgumentException();
	}

	@Override
	public void setCenterY(long gameY) {
		throw new IllegalArgumentException();
	}

	@Override
	public void setMinY(long newBottomY) {
		throw new IllegalArgumentException();
	}

	@Override
	public void setMinX(long newLeft) {
		throw new IllegalArgumentException();
	}

	@Override
	public int id() {
		throw new IllegalArgumentException();
	}

	@Override
	public void setMaxX(long newRight) {
		throw new IllegalArgumentException();
	}

	@Override
	public void setMaxY(long newTopY) {
		throw new IllegalArgumentException();
	}

	@Override
	public long getCenterX() {
		return (minX + maxX) / 2;
	}

	@Override
	public long getCenterY() {
		return (minY + maxY) / 2;
	}

}
