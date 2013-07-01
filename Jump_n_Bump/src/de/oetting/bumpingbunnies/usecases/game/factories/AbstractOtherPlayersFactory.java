package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.List;

import android.os.Parcelable;
import de.oetting.bumpingbunnies.usecases.game.communication.InformationSupplier;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.configuration.AiModus;
import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;

public abstract class AbstractOtherPlayersFactory implements Parcelable {

	public static AbstractOtherPlayersFactory initNetwork(MySocket socket,
			int index) {
		return new NetworkFactory(socket, index);
	}

	public static AbstractOtherPlayersFactory initSinglePlayer(AiModus aiModus) {
		return new SingleplayerFactory(aiModus);
	}

	public abstract AbstractInputServiceFactory getInputServiceFactory();

	public abstract InformationSupplier createInformationSupplier(
			List<MySocket> allSockets);

	public abstract AbstractStateSenderFactory createStateSenderFactory();

	public abstract RemoteSender createSender();

	protected AbstractOtherPlayersFactory() {
	}

}
