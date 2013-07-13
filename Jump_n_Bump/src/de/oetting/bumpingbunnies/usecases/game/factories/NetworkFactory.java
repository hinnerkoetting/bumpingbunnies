package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.communication.StorableSocket;
import de.oetting.bumpingbunnies.usecases.game.communication.IncomingNetworkDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkReceiverDispatcherThreadFactory;

public class NetworkFactory extends AbstractOtherPlayersFactory implements
		Parcelable {

	private final MySocket socket;
	private final int index;

	public NetworkFactory(MySocket socket, int index) {
		this.socket = socket;
		this.index = index;
	}

	public NetworkFactory(Parcel in) {
		StorableSocket storeSocket = new StorableSocket(in);
		this.socket = storeSocket.getStoredSocket();
		this.index = in.readInt();
	}

	@Override
	public AbstractInputServiceFactory getInputServiceFactory() {
		return new NetworkInputServiceFactory();
	}

	@Override
	public NetworkReceiver createInformationSupplier(
			List<RemoteSender> allSender,
			IncomingNetworkDispatcher networkDispatcher) {
		return NetworkReceiverDispatcherThreadFactory
				.createGameNetworkReceiver(this.socket, allSender,
						(NetworkToGameDispatcher) networkDispatcher);
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		StorableSocket storeSocket = new StorableSocket(this.socket, this.index);
		storeSocket.writeToParcel(dest, flags);
		dest.writeInt(this.index);
	}

}
