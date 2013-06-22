package de.jumpnbump.usecases.game.communication.factories;

import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.configuration.OtherPlayerConfiguration;
import de.jumpnbump.usecases.game.model.Player;

public abstract class AbstractStateSenderFactory {

	public abstract StateSender create(Player player,
			OtherPlayerConfiguration configuration);
}
