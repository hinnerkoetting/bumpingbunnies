package de.oetting.bumpingbunnies.core.networking.sender;

import java.util.HashMap;
import java.util.Map;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateMessage;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class PlayerStateSender {

	private Map<Integer, Long> currentMessageCounter = new HashMap<Integer, Long>();

	private final NetworkSender sender;

	public PlayerStateSender(NetworkSender sender) {
		this.sender = sender;
	}

	public void sendState(Bunny player) {
		if (ownerOfSocketNeedsToKnowThisPlayersState(player)) {
			PlayerStateMessage message = new PlayerStateMessage(getNextMessageCounter(player), player.getState());
			sender.sendMessage(MessageId.PLAYER_POS, message);
		}
	}

	private boolean ownerOfSocketNeedsToKnowThisPlayersState(Bunny player) {
		boolean isLocalPlayer = player.getOpponent().isLocalPlayer();
		boolean playerIsControlledByOwnerOfSocket = belongsToPlayer(player);
		return isLocalPlayer || !playerIsControlledByOwnerOfSocket && player.getOpponent().isDirectlyConnected();
	}

	private long getNextMessageCounter(Bunny player) {
		assertEntryExists(player);
		long currentId = currentMessageCounter.get(player.id());
		long nextId = currentId + 1;
		currentMessageCounter.put(player.id(), nextId);
		return nextId;
	}

	private void assertEntryExists(Bunny player) {
		if (!currentMessageCounter.containsKey(player.id()))
			currentMessageCounter.put(player.id(), 0L);
	}

	public boolean belongsToPlayer(Bunny player) {
		return sender.isConnectionToPlayer(player.getOpponent());
	}

	public boolean belongsToSocket(MySocket socket) {
		return sender.isConnectionToPlayer(socket.getConnectionIdentifier());
	}

	public void cancel() {
		sender.cancel();
	}
}
