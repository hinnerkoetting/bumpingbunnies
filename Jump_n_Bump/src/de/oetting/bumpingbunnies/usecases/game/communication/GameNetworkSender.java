package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class GameNetworkSender implements StateSender {

	private final Player player;
	private final RemoteSender networkThread;

	public GameNetworkSender(Player player, RemoteSender networkThread) {
		this.player = player;
		this.networkThread = networkThread;
	}

	@Override
	public void sendPlayerCoordinates() {
		this.networkThread.sendPlayerCoordinates(this.player);
	}

}
