package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.usecases.game.communication.InformationSupplier;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkReceiverDispatcherThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkSendQueueThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.StateSenderFactory;
import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.start.communication.StorableSocket;

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
	public InformationSupplier createInformationSupplier(
			List<RemoteSender> allSender) {
		return NetworkReceiverDispatcherThreadFactory.create(this.socket,
				allSender);
	}

	@Override
	public AbstractStateSenderFactory createStateSenderFactory() {
		return new StateSenderFactory();
	}

	@Override
	public RemoteSender createSender() {
		return NetworkSendQueueThreadFactory.create(this.socket);
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
