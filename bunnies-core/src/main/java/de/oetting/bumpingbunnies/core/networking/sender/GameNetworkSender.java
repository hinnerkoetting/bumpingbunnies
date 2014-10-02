package de.oetting.bumpingbunnies.core.networking.sender;

import de.oetting.bumpingbunnies.core.network.StateSender;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateMessage;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class GameNetworkSender implements StateSender {

	private static long currentMessageCounter = 0;
	private final Player player;
	private final NetworkSender connection;

	public GameNetworkSender(Player player, NetworkSender networkThread) {
		this.player = player;
		this.connection = networkThread;
	}

	@Override
	public void sendPlayerCoordinates() {
		PlayerStateMessage message = new PlayerStateMessage(currentMessageCounter++, this.player.getState());
		this.connection.sendMessageFast(MessageId.SEND_PLAYER_STATE, message);
	}

	@Override
	public boolean sendsStateToPlayer(Player p) {
		return this.connection.isConnectionToPlayer(p.getOpponent());
	}

}
