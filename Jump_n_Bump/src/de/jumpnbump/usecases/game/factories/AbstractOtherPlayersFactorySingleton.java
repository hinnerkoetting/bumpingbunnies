package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.RemoteSender;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.jumpnbump.usecases.game.configuration.AiModus;
import de.jumpnbump.usecases.start.communication.MySocket;

public abstract class AbstractOtherPlayersFactorySingleton {

	private static AbstractOtherPlayersFactorySingleton singleton;

	public static void initNetwork(MySocket socket) {
		singleton = new NetworkFactorySingleton(socket);
	}

	public static void initSinglePlayer(AiModus aiModus) {
		singleton = new SingleplayerFactorySingleton(aiModus);
	}

	public static AbstractOtherPlayersFactorySingleton getSingleton() {
		if (singleton == null) {
			throw new IllegalArgumentException("Init first");
		}
		return singleton;
	}

	public abstract AbstractInputServiceFactory getInputServiceFactory();

	public abstract InformationSupplier createInformationSupplier();

	public abstract AbstractStateSenderFactory createStateSenderFactory(
			RemoteSender sender);

	public abstract RemoteSender createSender();

}
