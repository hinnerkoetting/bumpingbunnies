package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageParser;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.MessageParserFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.factories.communication.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class NetworkSendControl implements PlayerJoinListener {

	private final List<NetworkSender> sendThreads;
	private final RemoteConnectionFactory factory;
	private final MessageParser messageParser;

	public NetworkSendControl(RemoteConnectionFactory factory) {
		this(factory, new CopyOnWriteArrayList<NetworkSender>());
	}

	public NetworkSendControl(RemoteConnectionFactory factory, List<NetworkSender> sendThreads) {
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
