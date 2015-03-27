package de.oetting.bumpingbunnies.core.worldCreation;

import java.io.IOException;
import java.io.InputStream;

public class NonClosingInputstream extends InputStream {

	private final InputStream delegate;

	public NonClosingInputstream(InputStream delegate) {
		this.delegate = delegate;
	}

	public int read() throws IOException {
		return delegate.read();
	}

	public int read(byte[] b) throws IOException {
		return delegate.read(b);
	}

	public int read(byte[] b, int off, int len) throws IOException {
		return delegate.read(b, off, len);
	}

	public long skip(long n) throws IOException {
		return delegate.skip(n);
	}

	public String toString() {
		return delegate.toString();
	}

	public int available() throws IOException {
		return delegate.available();
	}

	public void close() throws IOException {
		// do nothing
	}

	public void mark(int readlimit) {
		delegate.mark(readlimit);
	}

	public void reset() throws IOException {
		delegate.reset();
	}

	public boolean markSupported() {
		return delegate.markSupported();
	}

}
