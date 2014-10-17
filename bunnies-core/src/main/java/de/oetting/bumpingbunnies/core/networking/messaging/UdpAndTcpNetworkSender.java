package de.oetting.bumpingbunnies.core.networking.messaging;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.network.JsonWrapper;
import de.oetting.bumpingbunnies.model.network.MessageId;

/**
 * Consists of two connections. One is more stable, one is faster.
 * 
 */
public class UdpAndTcpNetworkSender implements NetworkSender {

	private final NetworkSender tcpConnection;
	private final NetworkSender udpConnection;
	private final ConnectionIdentifier owner;

	public UdpAndTcpNetworkSender(NetworkSendQueueThread tcpConnection, NetworkSender udpConnection, ConnectionIdentifier owner) {
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
	public boolean isConnectionToPlayer(ConnectionIdentifier opponent) {
		return this.owner.equals(opponent);
	}

	@Override
	public void start() {
		tcpConnection.start();
		udpConnection.start();
	}

}
