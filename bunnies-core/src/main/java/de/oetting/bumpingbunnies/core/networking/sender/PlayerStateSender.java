package de.oetting.bumpingbunnies.core.networking.sender;

import java.util.HashMap;
import java.util.Map;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateMessage;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class PlayerStateSender {

	private Map<Integer, Long> currentMessageCounter = new HashMap<Integer, Long>();

	private final NetworkSender sender;

	public PlayerStateSender(NetworkSender sender) {
		this.sender = sender;
	}

	public void sendState(Player player) {
		if (!belongsToPlayer(player)) {
			synchronized (player) {
				PlayerStateMessage message = new PlayerStateMessage(getNextMessageCounter(player), player.getState());
				sender.sendMessageFast(MessageId.SEND_PLAYER_STATE, message);
			}
		}
	}

	private long getNextMessageCounter(Player player) {
		assertEntryExists(player);
		long currentId = currentMessageCounter.get(player.id());
		long nextId = currentId + 1;
		currentMessageCounter.put(player.id(), nextId);
		return nextId;
	}

	private void assertEntryExists(Player player) {
		if (!currentMessageCounter.containsKey(player.id()))
			currentMessageCounter.put(player.id(), 0L);
	}

	public boolean belongsToPlayer(Player player) {
		return sender.isConnectionToPlayer(player.getOpponent());
	}

	public boolean belongsToSocket(MySocket socket) {
		return sender.isConnectionToPlayer(socket.getOwner());
	}
}
