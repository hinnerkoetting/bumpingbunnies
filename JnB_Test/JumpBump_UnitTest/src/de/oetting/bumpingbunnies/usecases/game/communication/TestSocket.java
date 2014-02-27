package de.oetting.bumpingbunnies.usecases.game.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.oetting.bumpingbunnies.communication.AbstractSocket;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.TestOpponentFactory;

public class TestSocket extends AbstractSocket {

	private final OutputStream os;
	private final InputStream is;

	public TestSocket(OutputStream os, InputStream is) {
		super(TestOpponentFactory.createDummyOpponent());
		this.os = os;
		this.is = is;
	}

	public TestSocket() {
		super(TestOpponentFactory.createDummyOpponent());
		this.os = new OutputStream() {

			@Override
			public void write(int arg0) throws IOException {
			}
		};
		this.is = new InputStream() {

			@Override
			public int read() throws IOException {
				return 0;
			}
		};
	}

	@Override
	public void close() throws IOException {

	}

	@Override
	public void connect() {
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.is;
	}

	@Override
	protected OutputStream getOutputStream() throws IOException {
		return this.os;
	}

	@Override
	public boolean isFastSocketPossible() {
		return false;
	}

}
