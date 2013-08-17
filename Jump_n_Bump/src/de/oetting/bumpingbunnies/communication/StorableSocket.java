package de.oetting.bumpingbunnies.communication;

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
public class StorableSocket extends AbstractSocket implements MySocket, Parcelable {

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

	public StorableSocket(Parcel in) throws IOException {
		this(in.readInt());
	}

	public StorableSocket(int index) throws IOException {
		super(SocketStorage.getSingleton().getSocket(index).getOutputStream());
		this.index = index;
		this.cachedSocket = SocketStorage.getSingleton().getSocket(index);
	}

	public StorableSocket(MySocket original, int index) throws IOException {
		super(original.getOutputStream());
		this.index = index;
		this.cachedSocket = original;
	}

	public MySocket getStoredSocket() {
		return this.cachedSocket;
	}
}
