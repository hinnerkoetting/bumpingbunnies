package de.oetting.bumpingbunnies.core.networking.messaging.playerDisconnected;

import de.oetting.bumpingbunnies.model.game.objects.Opponent;

public class PlayerDisconnectedMessage {

	private final Opponent opponent;

	public PlayerDisconnectedMessage(Opponent opponent) {
		this.opponent = opponent;
	}

	public Opponent getOpponent() {
		return opponent;
	}

}
