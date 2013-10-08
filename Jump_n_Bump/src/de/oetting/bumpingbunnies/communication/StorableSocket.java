package de.oetting.bumpingbunnies.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

/**
 * Can be stored through parcelables.
 * 
 */
@SuppressLint("ParcelCreator")
public class StorableSocket extends AbstractSocket implements MySocket, Parcelable {

	private int index;
	private MySocket cachedSocket;

	@Override
	public void close() throws IOException {
		this.cachedSocket.close();
	}

	@Override
	public void connect() {
		this.cachedSocket.connect();
	}

	@Override
	public InputStream getInputStream() throws IOException {
		throw new IllegalArgumentException("Storable socket does not support getInputstream");
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(this.index);
		getOwner().writeToParcel(dest, flags);
	}

	public StorableSocket(Parcel in) throws IOException {
		this(in.readInt(), new Opponent(in));
	}

	public StorableSocket(int index, Opponent opponent) throws IOException {
		super(opponent);
		this.index = index;
		this.cachedSocket = SocketStorage.getSingleton().getSocket(index);
	}

	public StorableSocket(MySocket original, int index, Opponent opponent) throws IOException {
		super(opponent);
		this.index = index;
		this.cachedSocket = original;
	}

	public MySocket getStoredSocket() {
		return this.cachedSocket;
	}

	@Override
	protected OutputStream getOutputStream() throws IOException {
		throw new IllegalArgumentException("Storable socket does not support getOutputStream");
	}

	@Override
	public MySocket createFastConnection() {
		return this;
	}

	int getIndex() {
		return this.index;
	}
}
