package de.oetting.bumpingbunnies.communication;

import java.io.IOException;

import de.oetting.bumpingbunnies.core.networking.MySocket;

public class DummyServerSocket implements ServerSocket {

	@Override
	public MySocket accept() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close() throws IOException {
	}

}
