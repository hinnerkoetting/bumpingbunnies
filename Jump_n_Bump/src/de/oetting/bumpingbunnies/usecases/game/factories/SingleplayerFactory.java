package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.usecases.game.communication.DummyInformationSupplier;
import de.oetting.bumpingbunnies.usecases.game.communication.InformationSupplier;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.DummyStateSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.NetworkSendQueueThreadFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.AiModus;
import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;

public class SingleplayerFactory extends AbstractOtherPlayersFactory implements
		Parcelable {

	private final AiModus aiModus;

	public SingleplayerFactory(AiModus aiModus) {
		this.aiModus = aiModus;
	}

	public SingleplayerFactory(Parcel in) {
		this.aiModus = AiModus.valueOf(in.readString());
	}

	@Override
	public AbstractInputServiceFactory getInputServiceFactory() {
		return this.aiModus.createAiModeFactoryClass();
	}

	@Override
	public InformationSupplier createInformationSupplier(
			List<MySocket> allSockets) {
		return new DummyInformationSupplier();
	}

	@Override
	public AbstractStateSenderFactory createStateSenderFactory() {
		return new DummyStateSenderFactory();
	}

	@Override
	public RemoteSender createSender() {
		return NetworkSendQueueThreadFactory.createDummyRemoteSender();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.aiModus.toString());
	}

}
