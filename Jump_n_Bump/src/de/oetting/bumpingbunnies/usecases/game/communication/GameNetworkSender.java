package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.communication.RemoteConnection;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class GameNetworkSender implements StateSender {

	private final Player player;
	private final RemoteConnection connection;

	public GameNetworkSender(Player player, RemoteConnection networkThread) {
		this.player = player;
		this.connection = networkThread;
	}

	@Override
	public void sendPlayerCoordinates() {
		this.connection.sendFast(MessageId.SEND_PLAYER_STATE, this.player.getState());
	}

	@Override
	public RemoteSender getRemoteSender() {
		return this.connection;
	}

}
