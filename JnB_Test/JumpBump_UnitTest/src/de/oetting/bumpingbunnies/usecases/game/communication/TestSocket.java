package de.oetting.bumpingbunnies.usecases.game.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.oetting.bumpingbunnies.communication.AbstractSocket;

public class TestSocket extends AbstractSocket {

	private final OutputStream os;

	public TestSocket(OutputStream os) {
		super(os);
		this.os = os;
	}

	@Override
	public void close() throws IOException {

	}

	@Override
	public void connect() throws IOException {

	}

	@Override
	public InputStream getInputStream() throws IOException {
		return null;
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return this.os;
	}

}
