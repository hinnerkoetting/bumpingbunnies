package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.StateSender;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateMessage;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

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
