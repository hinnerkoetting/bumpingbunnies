package de.oetting.bumpingbunnies.communication;

import de.oetting.bumpingbunnies.usecases.game.communication.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public class RemoteConnection implements RemoteSender {

	private final RemoteSender tcpConnection;
	private final RemoteSender udpConnection;
	private final Opponent owner;

	public RemoteConnection(NetworkSendQueueThread tcpConnection, RemoteSender udpConnection, Opponent owner) {
		this.tcpConnection = tcpConnection;
		this.udpConnection = udpConnection;
		this.owner = owner;
	}

	public void sendReliable(MessageId messageId, Object message) {
		this.tcpConnection.sendMessage(messageId, message);
	}

	public void sendFast(MessageId messageId, Object message) {
		this.udpConnection.sendMessage(messageId, message);
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
	public void sendMessageWithChecksum(MessageId id, Object message) {
		this.udpConnection.sendMessageWithChecksum(id, message);
	}

	public boolean isConnectionToPlayer(Opponent opponent) {
		return this.owner.equals(opponent);
	}

}
