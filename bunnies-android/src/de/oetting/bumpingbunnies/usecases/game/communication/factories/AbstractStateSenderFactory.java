package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public abstract class AbstractStateSenderFactory {

	public abstract StateSender create(Player player,
			OpponentConfiguration configuration);
}
