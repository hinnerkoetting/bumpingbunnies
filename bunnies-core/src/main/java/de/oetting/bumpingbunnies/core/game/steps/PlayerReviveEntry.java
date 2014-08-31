package de.oetting.bumpingbunnies.core.game.steps;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerReviveEntry {

	private final long earliestReviveTime;
	private final Player player;

	public PlayerReviveEntry(long earliestReviveTime, Player player) {
		super();
		this.earliestReviveTime = earliestReviveTime;
		this.player = player;
	}

	public long getEarliestReviveTime() {
		return this.earliestReviveTime;
	}

	public Player getPlayer() {
		return this.player;
	}

}
