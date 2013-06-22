package de.jumpnbump.usecases.game.communication.factories;

import de.jumpnbump.usecases.game.communication.DummyStateSender;
import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.configuration.OtherPlayerConfiguration;
import de.jumpnbump.usecases.game.model.Player;

public class DummyStateSenderFactory extends AbstractStateSenderFactory {

	@Override
	public StateSender create(Player player,
			OtherPlayerConfiguration configuration) {
		return new DummyStateSender();
	}

}
