package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
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
		this.networkThread.sendMessage(MessageId.SEND_PLAYER_STATE, this.player.getState());
	}

	@Override
	public RemoteSender getRemoteSender() {
		return this.networkThread;
	}

}
