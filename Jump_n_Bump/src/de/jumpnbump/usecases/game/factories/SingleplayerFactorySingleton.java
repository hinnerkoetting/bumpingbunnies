package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.communication.DummyInformationSupplier;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.jumpnbump.usecases.game.communication.factories.DummyStateSenderFactory;

public class SingleplayerFactorySingleton extends
		AbstractInputServiceFactorySingleton {

	@Override
	public AbstractInputServiceFactory getInputServiceFactory() {
		return new DummyInputServiceFactory();
	}

	@Override
	public InformationSupplier createInformationSupplier() {
		return new DummyInformationSupplier();
	}

	@Override
	public AbstractStateSenderFactory createStateSenderFactory() {
		return new DummyStateSenderFactory();
	}

}
