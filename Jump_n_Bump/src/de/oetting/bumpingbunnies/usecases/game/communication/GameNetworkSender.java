package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.communication.RemoteConnection;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.player.PlayerStateMessage;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class GameNetworkSender implements StateSender {

	private static long currentMessageCounter = 0;
	private final Player player;
	private final RemoteConnection connection;

	public GameNetworkSender(Player player, RemoteConnection networkThread) {
		this.player = player;
		this.connection = networkThread;
	}

	@Override
	public void sendPlayerCoordinates() {
		PlayerStateMessage message = new PlayerStateMessage(currentMessageCounter++, this.player.getState());
		this.connection.sendMessageWithChecksum(MessageId.SEND_PLAYER_STATE, message);
	}

	@Override
	public boolean sendsStateToPlayer(Player p) {
		return this.connection.isConnectionToPlayer(p);
	}

}
