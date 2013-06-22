package de.jumpnbump.usecases.game.factories;

import android.os.Parcel;
import android.os.Parcelable;
import de.jumpnbump.usecases.game.communication.DummyInformationSupplier;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.RemoteSender;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.jumpnbump.usecases.game.communication.factories.DummyStateSenderFactory;
import de.jumpnbump.usecases.game.communication.factories.NetworkSendQueueThreadFactory;
import de.jumpnbump.usecases.game.configuration.AiModus;

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
	public InformationSupplier createInformationSupplier() {
		return new DummyInformationSupplier();
	}

	@Override
	public AbstractStateSenderFactory createStateSenderFactory(
			RemoteSender sender) {
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
