package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.List;

import android.annotation.SuppressLint;
import android.os.Parcelable;
import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.communication.IncomingNetworkDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkReceiver;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.configuration.AiModus;

@SuppressLint("ParcelCreator")
public abstract class AbstractOtherPlayersFactory implements Parcelable {

	public static AbstractOtherPlayersFactory initNetwork(MySocket socket,
			int index) {
		return new NetworkFactory(socket, index);
	}

	public static AbstractOtherPlayersFactory initSinglePlayer(AiModus aiModus) {
		return new SingleplayerFactory(aiModus);
	}

	public abstract AbstractInputServiceFactory getInputServiceFactory();

	public abstract NetworkReceiver createInformationSupplier(
			List<RemoteSender> allSockets,
			IncomingNetworkDispatcher networkDispatcher);

	protected AbstractOtherPlayersFactory() {
	}

}
