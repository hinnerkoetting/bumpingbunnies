package de.oetting.bumpingbunnies.communication.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import de.oetting.bumpingbunnies.communication.AbstractSocket;
import de.oetting.bumpingbunnies.communication.IORuntimeException;
import de.oetting.bumpingbunnies.communication.MethodNotImplemented;
import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public class MyBluetoothSocket extends AbstractSocket implements MySocket {

	private final BluetoothSocket socket;

	public MyBluetoothSocket(BluetoothSocket socket, Opponent opponent) throws IOException {
		super(opponent);
		this.socket = socket;
	}

	@Override
	public void close() throws IOException {
		this.socket.close();
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
	public MySocket createFastConnection() {
		return this;
	}

}
