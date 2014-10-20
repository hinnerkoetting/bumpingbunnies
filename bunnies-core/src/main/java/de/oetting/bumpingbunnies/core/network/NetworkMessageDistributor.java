package de.oetting.bumpingbunnies.core.network;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.network.sockets.NewSocketListener;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.MessageParserFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;
import de.oetting.bumpingbunnies.model.network.MessageId;

/**
 * Distributes Messages to all clients.
 *
 */
public class NetworkMessageDistributor implements NewSocketListener {

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
	public void newEvent(MySocket socket) {
		NetworkSender newSender = this.factory.create(socket);
		newSender.start();
		this.sendThreads.add(newSender);
	}

	@Override
	public void removeEvent(MySocket socket) {
		NetworkSender sender = findSendThread(socket);
		sender.cancel();
		sendThreads.remove(sender);
	}

	private NetworkSender findSendThread(MySocket p) {
		for (NetworkSender sender : sendThreads)
			if (sender.usesThisSocket(p))
				return sender;
		throw new IllegalArgumentException("Could not find sendthread for socket " + p.getConnectionIdentifier());
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

	public NetworkSender findConnection(ConnectionIdentifier opponent) {
		NetworkSender rc = findConnectionOrNull(opponent);
		if (rc == null) {
			throw new ConnectionDoesNotExist();
		} else {
			return rc;
		}
	}

	private NetworkSender findConnectionOrNull(ConnectionIdentifier opponent) {
		for (NetworkSender rc : this.sendThreads) {
			if (rc.isConnectionToPlayer(opponent)) {
				return rc;
			}
		}
		return null;
	}

	public void removeSender(ConnectionIdentifier opponent) {
		LOGGER.info("Removing network sender");
		NetworkSender sender = findSenderForOpponent(opponent);
		sendThreads.remove(sender);
	}

	private NetworkSender findSenderForOpponent(ConnectionIdentifier opponent) {
		for (NetworkSender sender : sendThreads) {
			if (sender.isConnectionToPlayer(opponent))
				return sender;

		}
		throw new IllegalArgumentException("Could not find sendthread for " + opponent);
	}

	public void start() {
		for (NetworkSender sender : sendThreads)
			sender.start();
	}

	public static class ConnectionDoesNotExist extends RuntimeException {
	}

}
