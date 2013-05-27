package de.jumpnbump.usecases.game.factories;

import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.businesslogic.DummyInputService;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.model.Player;

public class DummyInputServiceFactory extends AbstractInputServiceFactory {

	@Override
	public InputService create(InformationSupplier reicerThread, Player player) {
		return new DummyInputService();
	}

}
