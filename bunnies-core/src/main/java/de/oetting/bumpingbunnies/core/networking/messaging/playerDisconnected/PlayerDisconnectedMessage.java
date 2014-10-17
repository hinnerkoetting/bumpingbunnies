package de.oetting.bumpingbunnies.core.networking.messaging.playerDisconnected;


public class PlayerDisconnectedMessage {

	private final int playerId;

	public PlayerDisconnectedMessage(int playerId) {
		this.playerId = playerId;
	}

	public int getPlayerId() {
		return playerId;
	}

}
