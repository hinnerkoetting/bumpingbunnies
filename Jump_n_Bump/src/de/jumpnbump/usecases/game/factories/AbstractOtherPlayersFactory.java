package de.jumpnbump.usecases.game.factories;

import android.os.Parcelable;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.RemoteSender;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.jumpnbump.usecases.game.configuration.AiModus;
import de.jumpnbump.usecases.start.communication.MySocket;

public abstract class AbstractOtherPlayersFactory implements Parcelable {

	public static AbstractOtherPlayersFactory initNetwork(MySocket socket,
			int index) {
		return new NetworkFactory(socket, index);
	}

	public static AbstractOtherPlayersFactory initSinglePlayer(AiModus aiModus) {
		return new SingleplayerFactory(aiModus);
	}

	public abstract AbstractInputServiceFactory getInputServiceFactory();

	public abstract InformationSupplier createInformationSupplier();

	public abstract AbstractStateSenderFactory createStateSenderFactory();

	public abstract RemoteSender createSender();

}
