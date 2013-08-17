package de.oetting.bumpingbunnies.communication.bluetooth;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.bluetooth.BluetoothSocket;
import de.oetting.bumpingbunnies.communication.AbstractSocket;
import de.oetting.bumpingbunnies.communication.MySocket;

public class MyBluetoothSocket extends AbstractSocket implements MySocket {

	private final BluetoothSocket socket;

	public MyBluetoothSocket(BluetoothSocket socket) throws IOException {
		super(socket.getOutputStream());
		this.socket = socket;
	}

	@Override
	public void close() throws IOException {
		this.socket.close();
	}

	@Override
	public void connect() throws IOException {
		this.socket.connect();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.socket.getInputStream();
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return this.socket.getOutputStream();
	}

}
