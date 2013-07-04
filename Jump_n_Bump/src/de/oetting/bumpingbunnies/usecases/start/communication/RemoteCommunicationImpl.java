package de.oetting.bumpingbunnies.usecases.start.communication;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.MyLog;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class RemoteCommunicationImpl implements RemoteCommunication {
	private static final MyLog LOGGER = Logger
			.getLogger(RemoteCommunicationImpl.class);
	private final RoomActivity origin;
	private AcceptThread acceptThread;
	private ConnectThread connectThread;
	private SocketFactory serverSocketFactory;

	public RemoteCommunicationImpl(RoomActivity origin,
			SocketFactory serverSocketFactory) {
		this.origin = origin;
		this.serverSocketFactory = serverSocketFactory;
		this.acceptThread = new DummyAcceptThread();
		this.connectThread = new DummyConnectThread();
	}

	@Override
	public void startServer() {
		LOGGER.info("Starting server");
		closeOpenConnections();
		this.acceptThread = new AcceptThreadImpl(
				this.serverSocketFactory.create(), this.origin);
		this.acceptThread.start();
	}

	@Override
	public void closeOpenConnections() {
		LOGGER.info("Closing connections");
		this.acceptThread.close();
		this.connectThread.close();
		SocketStorage.getSingleton().closeExistingSocket();
	}

	@Override
	public void connectToServer(ServerDevice device) {
		LOGGER.info("Connecting to server");
		this.connectThread = new ConnectThreadImpl(device.createClientSocket(),
				this.origin);
		this.connectThread.start();
	}

	@Override
	public boolean activate() {
		return true;
	}

	@Override
	public void findServer(String address) {
	}

}
