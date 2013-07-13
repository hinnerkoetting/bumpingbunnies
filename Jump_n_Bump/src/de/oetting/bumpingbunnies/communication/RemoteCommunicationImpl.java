package de.oetting.bumpingbunnies.communication;

import android.widget.Toast;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.networkRoom.RoomActivity;

public class RemoteCommunicationImpl implements RemoteCommunication {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RemoteCommunicationImpl.class);
	private final RoomActivity origin;
	private final SocketFactory serverSocketFactory;
	private AcceptThread acceptThread;
	private ConnectThread connectThread;

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
	public void connectToServer(final ServerDevice device) {
		LOGGER.info("Connecting to server");
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					RemoteCommunicationImpl.this.connectThread = new ConnectThreadImpl(
							RemoteCommunicationImpl.this.serverSocketFactory.createClientSocket(device),
							RemoteCommunicationImpl.this.origin);
					RemoteCommunicationImpl.this.connectThread.start();
				} catch (Exception e) {
					LOGGER.error("Error", e);
					displayCouldNotConnectException();
				}
			}
		}).start();

	}

	private void displayCouldNotConnectException() {
		CharSequence text = this.origin.getText(R.string.could_not_connect);
		Toast.makeText(this.origin, text, Toast.LENGTH_SHORT).show();
	}

	@Override
	public boolean activate() {
		return true;
	}

	@Override
	public void findServer(String address) {
	}

}
