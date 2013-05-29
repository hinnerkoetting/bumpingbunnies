package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.communication.DummyInformationSupplier;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.RemoteSender;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.jumpnbump.usecases.game.communication.factories.DummyStateSenderFactory;
import de.jumpnbump.usecases.game.communication.factories.NetworkSendQueueThreadFactory;
import de.jumpnbump.usecases.game.configuration.AiModus;

public class SingleplayerFactorySingleton extends
		AbstractOtherPlayersFactorySingleton {

	private final AiModus aiModus;

	public SingleplayerFactorySingleton(AiModus aiModus) {
		this.aiModus = aiModus;
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

}
