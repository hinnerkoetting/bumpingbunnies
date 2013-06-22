package de.jumpnbump.usecases.game.factories;

import android.os.Parcel;
import android.os.Parcelable;
import de.jumpnbump.usecases.MyApplication;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.RemoteSender;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.jumpnbump.usecases.game.communication.factories.NetworkReceiverDispatcherThreadFactory;
import de.jumpnbump.usecases.game.communication.factories.NetworkSendQueueThreadFactory;
import de.jumpnbump.usecases.game.communication.factories.StateSenderFactory;
import de.jumpnbump.usecases.start.communication.MySocket;
import de.jumpnbump.usecases.start.communication.StorableSocket;

public class NetworkFactory extends AbstractOtherPlayersFactory implements
		Parcelable {

	private final MySocket socket;
	private final int index;

	public NetworkFactory(MySocket socket, int index) {
		this.socket = socket;
		this.index = index;
	}

	public NetworkFactory(Parcel in, MyApplication application) {
		StorableSocket storeSocket = new StorableSocket(application, in);
		this.socket = storeSocket.getStoredSocket();
		this.index = in.readInt();
	}

	@Override
	public AbstractInputServiceFactory getInputServiceFactory() {
		return new NetworkInputServiceFactory();
	}

	@Override
	public InformationSupplier createInformationSupplier() {
		return NetworkReceiverDispatcherThreadFactory.create(this.socket);
	}

	@Override
	public AbstractStateSenderFactory createStateSenderFactory(
			RemoteSender sender) {
		return new StateSenderFactory(sender);
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
