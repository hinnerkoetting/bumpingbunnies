package de.oetting.bumpingbunnies.communication;

import de.oetting.bumpingbunnies.usecases.game.communication.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.usecases.game.communication.ThreadedNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

/**
 * Consists of two connections. One is more stable, one is faster.
 * 
 */
public class DividedNetworkSender implements ThreadedNetworkSender {

	private final ThreadedNetworkSender tcpConnection;
	private final ThreadedNetworkSender udpConnection;
	private final Opponent owner;

	public DividedNetworkSender(NetworkSendQueueThread tcpConnection, ThreadedNetworkSender udpConnection, Opponent owner) {
		this.tcpConnection = tcpConnection;
		this.udpConnection = udpConnection;
		this.owner = owner;
	}

	@Override
	public void cancel() {
		this.udpConnection.cancel();
		this.tcpConnection.cancel();
	}

	@Override
	public void sendMessage(MessageId id, Object message) {
		this.tcpConnection.sendMessage(id, message);
	}

	@Override
	public void sendMessage(JsonWrapper wrapper) {
		this.tcpConnection.sendMessage(wrapper);
	}

	@Override
	public boolean usesThisSocket(MySocket socket) {
		return this.tcpConnection.usesThisSocket(socket);
	}

	@Override
	public void start() {
		this.tcpConnection.start();
		this.udpConnection.start();
	}

	@Override
	public void sendMessageFast(MessageId id, Object message) {
		this.tcpConnection.sendMessage(id, message);
	}

	@Override
	public boolean isConnectionToPlayer(Opponent opponent) {
		return this.owner.equals(opponent);
	}

}
