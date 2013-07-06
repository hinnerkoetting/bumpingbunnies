package de.oetting.bumpingbunnies.usecases.game.model;

import android.graphics.Color;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;

public class Water implements GameObject {

	private final Rect rect;

	public Water(int minX, int minY, int maxX, int maxY) {
		this.rect = new Rect(minX, maxX, minY, maxY);
	}

	public Water(Rect rect) {
		super();
		this.rect = rect;
	}

	@Override
	public int maxX() {
		return this.rect.getMaxX();
	}

	@Override
	public int maxY() {
		return this.rect.getMaxY();
	}

	@Override
	public int minX() {
		return this.rect.getMinX();
	}

	@Override
	public int minY() {
		return this.rect.getMinY();
	}

	@Override
	public int getColor() {
		return Color.BLUE & 0x88FFFFFF;
	}

	@Override
	public int accelerationOnThisGround() {
		return ModelConstants.ACCELERATION_X_WATER;
	}

	@Override
	public void interactWithPlayerOnTop(Player p) {
	}

	@Override
	public void handleCollisionWithPlayer(Player player,
			CollisionDetection collisionDetection) {
		player.setMovementY((int) (player.movementY() * 0.99));
		player.setAccelerationY(ModelConstants.PLAYER_GRAVITY_WATER);
	}

}
