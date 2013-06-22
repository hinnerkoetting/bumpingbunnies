package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.RemoteSender;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.jumpnbump.usecases.game.configuration.AiModus;
import de.jumpnbump.usecases.start.communication.MySocket;

public abstract class AbstractOtherPlayersFactorySingleton {

	private static AbstractOtherPlayersFactorySingleton singleton;

	public static AbstractOtherPlayersFactorySingleton initNetwork(
			MySocket socket) {
		return new NetworkFactorySingleton(socket);
	}

	public static AbstractOtherPlayersFactorySingleton initSinglePlayer(
			AiModus aiModus) {
		return new SingleplayerFactorySingleton(aiModus);
	}

	public abstract AbstractInputServiceFactory getInputServiceFactory();

	public abstract InformationSupplier createInformationSupplier();

	public abstract AbstractStateSenderFactory createStateSenderFactory(
			RemoteSender sender);

	public abstract RemoteSender createSender();

}
