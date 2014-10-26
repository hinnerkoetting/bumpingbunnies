package de.oetting.bumpingbunnies.model.game.objects;

public class PlayerSimulation implements GameObject {

	private Rect rect;

	@Override
	public long maxX() {
		return rect.getMaxX();
	}

	@Override
	public long maxY() {
		return rect.getMaxY();
	}

	@Override
	public long minX() {
		return rect.getMinX();
	}

	@Override
	public long minY() {
		return rect.getMinY();
	}

	@Override
	public int accelerationOnThisGround() {
		throw new IllegalArgumentException("The simulated player must not interact with other players.");
	}

	@Override
	public void interactWithPlayerOnTop(Player p) {
		throw new IllegalArgumentException("The simulated player must not interact with other players.");
	}

}
