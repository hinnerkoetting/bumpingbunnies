package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import de.oetting.bumpingbunnies.usecases.game.communication.DummyStateSender;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.configuration.OtherPlayerConfiguration;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class DummyStateSenderFactory extends AbstractStateSenderFactory {

	@Override
	public StateSender create(Player player,
			OtherPlayerConfiguration configuration) {
		return new DummyStateSender();
	}

}
