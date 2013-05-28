package de.jumpnbump.usecases.game.communication.factories;

import de.jumpnbump.usecases.game.communication.DummyStateSender;
import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.model.Player;

public class DummyStateSenderFactory extends AbstractStateSenderFactory {

	@Override
	public StateSender create(Player player) {
		return new DummyStateSender();
	}

}
