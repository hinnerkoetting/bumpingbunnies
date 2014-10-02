package de.oetting.bumpingbunnies.core.network;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.MessageParserFactory;
import de.oetting.bumpingbunnies.model.networking.JsonWrapper;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

/**
 * Distributes Messages to all clients.
 *
 */
public class NetworkMessageDistributor implements PlayerJoinListener {

	private final List<NetworkSender> sendThreads;
	private final RemoteConnectionFactory factory;
	private final MessageParser messageParser;

	public NetworkMessageDistributor(RemoteConnectionFactory factory) {
		this(factory, new CopyOnWriteArrayList<NetworkSender>());
	}

	public NetworkMessageDistributor(RemoteConnectionFactory factory, List<NetworkSender> sendThreads) {
		super();
		this.factory = factory;
		this.sendThreads = sendThreads;
		this.messageParser = MessageParserFactory.create();
	}

	public List<NetworkSender> getSendThreads() {
		return this.sendThreads;
	}

	@Override
	public void newPlayerJoined(Player p) {
		NetworkSender newSender = this.factory.create(p);
		this.sendThreads.add(newSender);
	}

	@Override
	public void playerLeftTheGame(Player p) {
	}

	public void sendMessageExceptToOneSocket(JsonWrapper wrapper, MySocket incomingSocket) {
		for (NetworkSender queue : this.sendThreads) {
			if (!queue.usesThisSocket(incomingSocket)) {
				queue.sendMessage(wrapper);
			}
		}
	}

	public void sendMessage(MessageId messageId, Object message) {
		String encodedMessage = this.messageParser.encodeMessage(message);
		JsonWrapper wrapper = JsonWrapper.create(messageId, encodedMessage);
		for (NetworkSender sender : this.sendThreads) {
			sender.sendMessage(wrapper);
		}
	}

	public NetworkSender findConnection(Opponent opponent) {
		NetworkSender rc = findConnectionOrNull(opponent);
		if (rc == null) {
			throw new ConnectionDoesNotExist();
		} else {
			return rc;
		}
	}

	private NetworkSender findConnectionOrNull(Opponent opponent) {
		for (NetworkSender rc : this.sendThreads) {
			if (rc.isConnectionToPlayer(opponent)) {
				return rc;
			}
		}
		return null;
	}

	public static class ConnectionDoesNotExist extends RuntimeException {
	}
}
