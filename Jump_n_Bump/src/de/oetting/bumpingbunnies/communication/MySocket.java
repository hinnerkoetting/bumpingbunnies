package de.oetting.bumpingbunnies.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface MySocket {

	void close() throws IOException;

	void connect() throws IOException;

	InputStream getInputStream() throws IOException;

	OutputStream getOutputStream() throws IOException;

	void sendMessage(String message);
}
