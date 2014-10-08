package de.oetting.bumpingbunnies.core.networking.messaging.player;

import de.oetting.bumpingbunnies.model.game.objects.PlayerState;

/**
 * Wrapper for player-state with a counter. Every following message will have
 * the counter increased by one. Receivers can check the counter and only accept
 * messages which counter is bigger than the previous counter.
 * 
 */
public class PlayerStateMessage {

	private final long counter;
	private final PlayerState playerState;

	public PlayerStateMessage(long counter, PlayerState playerState) {
		this.counter = counter;
		this.playerState = playerState;
	}

	public long getCounter() {
		return this.counter;
	}

	public PlayerState getPlayerState() {
		return this.playerState;
	}

}
