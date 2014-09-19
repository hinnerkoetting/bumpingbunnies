package de.oetting.bumpingbunnies.communication;

import android.app.Activity;
import android.widget.Toast;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.ConnectsToServer;
import de.oetting.bumpingbunnies.usecases.networkRoom.AcceptsClientConnections;

public class RemoteCommunicationImpl implements RemoteCommunication {
	private static final Logger LOGGER = LoggerFactory
			.getLogger(RemoteCommunicationImpl.class);
	private final SocketFactory serverSocketFactory;
	private AcceptThread acceptThread;
	private ConnectThread connectThread;
	private final AcceptsClientConnections acceptsClientConnections;
	private final Activity activity;
	private ConnectsToServer connectsToServer;

	public RemoteCommunicationImpl(AcceptsClientConnections acceptsClientConnections, ConnectsToServer connectsToServer,
			Activity activity, SocketFactory serverSocketFactory) {
		this.acceptsClientConnections = acceptsClientConnections;
		this.connectsToServer = connectsToServer;
		this.serverSocketFactory = serverSocketFactory;
		this.activity = activity;
		this.acceptThread = new DummyAcceptThread();
		this.connectThread = new DummyConnectThread();
	}

	@Override
	public void startServer() {
		LOGGER.info("Starting server");
		closeOpenConnections();
		try {
			this.acceptThread = new AcceptThreadImpl(
					this.serverSocketFactory.create(), this.acceptsClientConnections);
			this.acceptThread.start();
		} catch (Exception e) {
			LOGGER.error("Exception when creating server", e);
			CharSequence text = this.activity.getText(R.string.could_not_open_server);
			Toast.makeText(this.activity, text, Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	public void closeOpenConnections() {
		LOGGER.info("Closing connections");
		this.acceptThread.close();
		// this.connectThread.close();
		// SocketStorage.getSingleton().closeExistingSocket();
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
							RemoteCommunicationImpl.this.connectsToServer);
					RemoteCommunicationImpl.this.connectThread.start();
				} catch (Exception e) {
					LOGGER.error("Error", e);
					displayCouldNotConnectException();
				}
			}
		}).start();

	}

	private void displayCouldNotConnectException() {
		this.activity.runOnUiThread(new Runnable() {

			@Override
			public void run() {
				CharSequence text = RemoteCommunicationImpl.this.activity.getText(R.string.could_not_connect);
				Toast.makeText(RemoteCommunicationImpl.this.activity, text, Toast.LENGTH_SHORT).show();
			}
		});

	}

	@Override
	public boolean activate() {
		return true;
	}

	@Override
	public void findServer(String address) {
	}

}
