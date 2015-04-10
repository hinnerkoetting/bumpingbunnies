package de.oetting.bumpingbunnies.core.network.room;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class DetailRoomEntry {

	private final RoomEntry entry;
	private final Bunny player;

	public DetailRoomEntry(RoomEntry entry, Bunny player) {
		this.entry = entry;
		this.player = player;
	}

	public RoomEntry getEntry() {
		return entry;
	}

	public Bunny getPlayer() {
		return player;
	}

	public String getPlayerName() {
		return entry.getPlayerName();
	}

	public int getPlayerId() {
		return entry.getPlayerId();
	}

	public long getCenterX() {
		return player.getCenterX();
	}

	public long getCenterY() {
		return player.getCenterY();
	}

	public int getAccelerationX() {
		return player.getAccelerationX();
	}

	public int getAccelerationY() {
		return player.getAccelerationY();
	}

	public int getMovementX() {
		return player.movementX();
	}

	public int getMovementY() {
		return player.movementY();
	}

	public boolean isFacingLeft() {
		return player.isFacingLeft();
	}

	public int getScore() {
		return player.getScore();
	}

	public boolean isDead() {
		return player.isDead();
	}

	public boolean isJumpingButtonPressed() {
		return player.isJumpingButtonPressed();
	}

}
