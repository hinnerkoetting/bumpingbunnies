package de.oetting.bumpingbunnies.core.networking;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import de.oetting.bumpingbunnies.core.game.OpponentTestFactory;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.AbstractSocket;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;

public class TestSocket extends AbstractSocket {

	private final OutputStream os;
	private final InputStream is;

	public TestSocket(OutputStream os, InputStream is) {
		super(OpponentTestFactory.create());
		this.os = os;
		this.is = is;
	}

	public TestSocket() {
		this(OpponentTestFactory.create());
	}

	public TestSocket(ConnectionIdentifier opponent) {
		super(opponent);
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
	public void close() {
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

	@Override
	public String getRemoteDescription() {
		return "TEST";
	}

	@Override
	public String getLocalDescription() {
		return "TEST";
	}

}
