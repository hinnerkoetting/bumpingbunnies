package de.oetting.bumpingbunnies.core.networking.sender;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateMessage;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class PlayerStateSender {

	private long currentMessageCounter = 0;

	private final NetworkSender sender;

	public PlayerStateSender(NetworkSender sender) {
		this.sender = sender;
	}

	public void sendState(Player player) {
		if (!belongsToPlayer(player)) {
			synchronized (player) {
				PlayerStateMessage message = new PlayerStateMessage(currentMessageCounter++, player.getState());
				sender.sendMessageFast(MessageId.SEND_PLAYER_STATE, message);
			}
		}
	}

	public boolean belongsToPlayer(Player player) {
		return sender.isConnectionToPlayer(player.getOpponent());
	}
}
