package de.oetting.bumpingbunnies.usecases.game.model;

import android.graphics.Bitmap;
import android.graphics.Color;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayer;

public class Water implements GameObjectWithImage {

	private final Rect rect;
	private final MusicPlayer musicPlayer;
	private Bitmap bitmap;

	public Water(long minX, long minY, long maxX, long maxY, MusicPlayer musicPlayer) {
		this.musicPlayer = musicPlayer;
		this.rect = new Rect(minX, maxX, minY, maxY);
	}

	public Water(Rect rect, MusicPlayer musicPlayer) {
		super();
		this.rect = rect;
		this.musicPlayer = musicPlayer;
	}

	@Override
	public long maxX() {
		return this.rect.getMaxX();
	}

	@Override
	public long maxY() {
		return this.rect.getMaxY();
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
		player.setExactMovementY((int) (player.movementY() * 0.99));
		if (player.movementY() <= ModelConstants.PLAYER_SPEED_WATER) {
			player.setExactMovementY(ModelConstants.PLAYER_SPEED_WATER);
		}
		player.setAccelerationY(ModelConstants.PLAYER_GRAVITY_WATER);
		if (isFirstTimeThePlayerHitsTheWater(player, collisionDetection)) {
			this.musicPlayer.start();
		}
	}

	/**
	 * If the simulated player is in the water and the player is not in the water this is the first time the player hits the water.
	 */
	private boolean isFirstTimeThePlayerHitsTheWater(Player player, CollisionDetection collisionDetection) {
		return !collisionDetection.collides(this, player);
	}

	@Override
	public Bitmap getBitmap() {
		return this.bitmap;
	}

	@Override
	public void setBitmap(Bitmap b) {
		this.bitmap = b;
	}

}
