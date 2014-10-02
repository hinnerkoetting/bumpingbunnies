package de.oetting.bumpingbunnies.core.networking.messaging;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.model.networking.JsonWrapper;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

/**
 * Consists of two connections. One is more stable, one is faster.
 * 
 */
public class UdpAndTcpNetworkSender implements NetworkSender {

	private final NetworkSender tcpConnection;
	private final NetworkSender udpConnection;
	private final Opponent owner;

	public UdpAndTcpNetworkSender(NetworkSendQueueThread tcpConnection, NetworkSender udpConnection, Opponent owner) {
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
	public void sendMessageFast(MessageId id, Object message) {
		// should be udp-connection but that does not work at the moment
		this.udpConnection.sendMessage(id, message);
	}

	@Override
	public boolean isConnectionToPlayer(Opponent opponent) {
		return this.owner.equals(opponent);
	}

}
