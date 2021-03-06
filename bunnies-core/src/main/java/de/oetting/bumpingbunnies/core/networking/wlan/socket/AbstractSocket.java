package de.oetting.bumpingbunnies.core.networking.wlan.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.NetworkConstants;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;

public abstract class AbstractSocket implements MySocket {

	private final ConnectionIdentifier owner;
	private Writer writer;
	private BufferedReader reader;
	private Object senderMonitor = new Object();
	private Object receiverMonitor = new Object();

	public AbstractSocket(ConnectionIdentifier owner) {
		super();
		this.owner = owner;
	}

	@Override
	public void sendMessage(String message) {
		synchronized (senderMonitor) {
			try {
				if (this.writer == null) {
					this.writer = createWriter(getOutputStream());
				}
				this.writer.write(message);
				this.writer.write('\n');
				this.writer.flush();
			} catch (IOException e) {
				throw new WriteFailed(e);
			}
		}
	}

	protected Writer createWriter(OutputStream os) {
		try {
			return new OutputStreamWriter(os);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String blockingReceive() {
		synchronized (receiverMonitor) {
			try {
				if (this.reader == null) {
					this.reader = new BufferedReader(new InputStreamReader(getInputStream(), NetworkConstants.ENCODING));
				}
				return this.reader.readLine();
			} catch (IOException e) {
				throw new ReadFailed(e);
			}
		}
	}

	protected abstract OutputStream getOutputStream() throws IOException;

	protected abstract InputStream getInputStream() throws IOException;

	public static class WriteFailed extends RuntimeException {
		public WriteFailed(Throwable throwable) {
			super(throwable);
		}
	}

	public static class ReadFailed extends RuntimeException {
		public ReadFailed(Throwable throwable) {
			super(throwable);
		}
	}

	@Override
	public ConnectionIdentifier getConnectionIdentifier() {
		return this.owner;
	}

}
