package de.oetting.bumpingbunnies.communication.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import de.oetting.bumpingbunnies.communication.AbstractSocket;
import de.oetting.bumpingbunnies.communication.IORuntimeException;
import de.oetting.bumpingbunnies.communication.MethodNotImplemented;
import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public class MyBluetoothSocket extends AbstractSocket implements MySocket {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyBluetoothSocket.class);
	private final BluetoothSocket socket;

	public MyBluetoothSocket(BluetoothSocket socket, Opponent opponent) throws IOException {
		super(opponent);
		this.socket = socket;
		LOGGER.info("Created bluetooth socket");
	}

	@Override
	public void close() {
        try {
            this.socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
	}

	@Override
	public void connect() {
		try {
			this.socket.connect();
		} catch (IOException e) {
			throw new IORuntimeException(e);
		}
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.socket.getInputStream();
	}

	@Override
	protected OutputStream getOutputStream() throws IOException {
		return this.socket.getOutputStream();
	}

	@Override
	public String blockingReceive() {
		throw new MethodNotImplemented();
	}

	@Override
	public boolean isFastSocketPossible() {
		return false;
	}

}
