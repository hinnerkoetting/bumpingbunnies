package de.oetting.bumpingbunnies.core.game.steps;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class PlayerReviveEntry {

	private final long earliestReviveTime;
	private final Bunny player;

	public PlayerReviveEntry(long earliestReviveTime, Bunny player) {
		super();
		this.earliestReviveTime = earliestReviveTime;
		this.player = player;
	}

	public long getEarliestReviveTime() {
		return this.earliestReviveTime;
	}

	public Bunny getPlayer() {
		return this.player;
	}

}
