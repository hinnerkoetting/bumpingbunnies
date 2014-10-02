package de.oetting.bumpingbunnies.usecases.game;

import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class TestableGameObject implements GameObject {

	public long maxX;
	public long maxY;
	public long minX;
	public long minY;
	public int color;
	public int accelerationOnThisGround;

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
	public int getColor() {
		return this.color;
	}

	@Override
	public int accelerationOnThisGround() {
		return this.accelerationOnThisGround;
	}

	@Override
	public void interactWithPlayerOnTop(Player p) {
	}

}
