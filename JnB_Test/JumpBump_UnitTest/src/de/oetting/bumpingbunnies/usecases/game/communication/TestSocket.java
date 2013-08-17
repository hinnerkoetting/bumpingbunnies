package de.oetting.bumpingbunnies.usecases.game.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.oetting.bumpingbunnies.communication.AbstractSocket;

public class TestSocket extends AbstractSocket {

	private final OutputStream os;
	private final InputStream is;

	public TestSocket(OutputStream os, InputStream is) {
		this.os = os;
		this.is = is;
	}

	@Override
	public void close() throws IOException {

	}

	@Override
	public void connect() throws IOException {

	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.is;
	}

	@Override
	protected OutputStream getOutputStream() throws IOException {
		return this.os;
	}

}
