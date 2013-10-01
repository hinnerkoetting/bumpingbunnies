package de.oetting.bumpingbunnies.communication;

import de.oetting.bumpingbunnies.usecases.game.communication.NetworkSendQueueThread;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;

public class RemoteConnection implements RemoteSender {

	private final RemoteSender tcpConnection;
	private final RemoteSender udpConnection;

	public RemoteConnection(NetworkSendQueueThread tcpConnection, RemoteSender udpConnection) {
		this.tcpConnection = tcpConnection;
		this.udpConnection = udpConnection;
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

}
