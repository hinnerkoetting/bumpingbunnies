package de.oetting.bumpingbunnies.communication;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.android.parcel.OpponentParceller;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.wlan.socket.AbstractSocket;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;

/**
 * Can be stored through parcelables.
 * 
 */
@SuppressLint("ParcelCreator")
public class StorableSocket extends AbstractSocket implements MySocket, Parcelable {

	private int index;
	private MySocket cachedSocket;

	@Override
	public void close() {
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
		new OpponentParceller().writeToParcel(getOwner(), dest);
	}

	public StorableSocket(Parcel in) throws IOException {
		this(in.readInt(), new OpponentParceller().createFromParcel(in));
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

	int getIndex() {
		return this.index;
	}

	@Override
	public boolean isFastSocketPossible() {
		return false;
	}

	@Override
	public String getRemoteDescription() {
		return cachedSocket.getRemoteDescription();
	}

	@Override
	public String getLocalDescription() {
		return cachedSocket.getLocalDescription();
	}
}
