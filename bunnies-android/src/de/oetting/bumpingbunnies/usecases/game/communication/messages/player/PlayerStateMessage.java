package de.oetting.bumpingbunnies.usecases.game.communication.messages.player;

import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

/**
 * Wrapper for player-state to allow a counter. The counter can be checked to be sure that no old playerstates are processed.
 * 
 */
public class PlayerStateMessage {

	private final long counter;
	private final PlayerState playerState;

	public PlayerStateMessage(long counter, PlayerState playerState) {
		super();
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
