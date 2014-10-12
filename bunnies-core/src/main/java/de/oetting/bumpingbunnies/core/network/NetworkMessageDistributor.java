package de.oetting.bumpingbunnies.core.network;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.MessageParserFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;
import de.oetting.bumpingbunnies.model.network.MessageId;

/**
 * Distributes Messages to all clients.
 *
 */
public class NetworkMessageDistributor implements PlayerJoinListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkMessageDistributor.class);

	private final List<NetworkSender> sendThreads;
	private final RemoteConnectionFactory factory;
	private final MessageParser messageParser;

	public NetworkMessageDistributor(RemoteConnectionFactory factory) {
		this(factory, new CopyOnWriteArrayList<NetworkSender>());
	}

	public NetworkMessageDistributor(RemoteConnectionFactory factory, List<NetworkSender> sendThreads) {
		this.factory = factory;
		this.sendThreads = sendThreads;
		this.messageParser = MessageParserFactory.create();
	}

	public List<NetworkSender> getSendThreads() {
		return this.sendThreads;
	}

	@Override
	public void newPlayerJoined(Player p) {
		if (p.getOpponent().isDirectlyConnected()) {
			NetworkSender newSender = this.factory.create(p);
			this.sendThreads.add(newSender);
		}
	}

	@Override
	public void playerLeftTheGame(Player p) {
		NetworkSender sender = findSendThread(p);
		sendThreads.remove(sender);
	}

	private NetworkSender findSendThread(Player p) {
		for (NetworkSender sender : sendThreads)
			if (sender.isConnectionToPlayer(p.getOpponent()))
				return sender;
		throw new IllegalArgumentException("Could not find sendthread for player " + p.id());
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

	public void removeSender(Opponent opponent) {
		LOGGER.info("Removing network sender");
		NetworkSender sender = findSenderForOpponent(opponent);
		sendThreads.remove(sender);
	}

	private NetworkSender findSenderForOpponent(Opponent opponent) {
		for (NetworkSender sender : sendThreads) {
			if (sender.isConnectionToPlayer(opponent))
				return sender;

		}
		throw new IllegalArgumentException("Could not find sendthread for " + opponent);
	}

	public static class ConnectionDoesNotExist extends RuntimeException {
	}
}
