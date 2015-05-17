package de.oetting.bumpingbunnies.core.networking.messaging.player;

import de.oetting.bumpingbunnies.model.game.objects.PlayerState;

/**
 * Wrapper for player-state with a counter. Every following message will have
 * the counter increased by one. Receivers can check the counter and only accept
 * messages which counter is bigger than the previous counter.
 * 
 */
public class PlayerStateMessage {

	//save bandwith by short names.
	//Counter of the last message.
	private final long c;
	//Position of player
	private final PlayerState p;

	public PlayerStateMessage(long counter, PlayerState playerState) {
		this.c = counter;
		this.p = playerState;
	}

	public long getCounter() {
		return this.c;
	}

	public PlayerState getPlayerState() {
		return this.p;
	}

}
