package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.List;

import android.annotation.SuppressLint;
import android.os.Parcel;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.usecases.game.communication.DummyInformationSupplier;
import de.oetting.bumpingbunnies.usecases.game.communication.IncomingNetworkDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.configuration.AiModus;

@SuppressLint("ParcelCreator")
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
	public NetworkReceiver createInformationSupplier(
			List<RemoteSender> allSockets,
			IncomingNetworkDispatcher networkDispatcher) {
		return new DummyInformationSupplier();
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
