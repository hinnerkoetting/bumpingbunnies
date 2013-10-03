package de.oetting.bumpingbunnies.communication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import de.oetting.bumpingbunnies.usecases.game.communication.NetworkConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public abstract class AbstractSocket implements MySocket {

	private final Opponent owner;
	private Writer writer;
	private BufferedReader reader;

	public AbstractSocket(Opponent owner) {
		super();
		this.owner = owner;
	}

	@Override
	public void sendMessage(String message) {
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

	protected Writer createWriter(OutputStream os) {
		try {
			return new OutputStreamWriter(os);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String blockingReceive() {
		try {
			if (this.reader == null) {
				this.reader = new BufferedReader(new InputStreamReader(getInputStream(), NetworkConstants.ENCODING));
			}
			return this.reader.readLine();
		} catch (IOException e) {
			throw new ReadFailed(e);
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
	public Opponent getOwner() {
		return this.owner;
	}
}
