package de.oetting.bumpingbunnies.usecases.start.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;

/**
 * Can be stored through parcelables.
 * 
 */
public class StorableSocket implements MySocket, Parcelable {

	private int index;
	private MySocket cachedSocket;

	@Override
	public void close() throws IOException {
		this.cachedSocket.close();
	}

	@Override
	public void connect() throws IOException {
		this.cachedSocket.connect();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		return this.cachedSocket.getInputStream();
	}

	@Override
	public OutputStream getOutputStream() throws IOException {
		return this.cachedSocket.getOutputStream();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.index);
	}

	public StorableSocket(Parcel in) {
		this(in.readInt());
	}

	public StorableSocket(int index) {
		this.index = index;
		this.cachedSocket = SocketStorage.getSingleton().getSocket(index);
	}

	public StorableSocket(MySocket original, int index) {
		this.index = index;
		this.cachedSocket = original;
	}

	public MySocket getStoredSocket() {
		return this.cachedSocket;
	}
}
