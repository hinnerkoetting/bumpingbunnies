package de.oetting.bumpingbunnies.usecases.game.factories.businessLogic;

import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface StateSenderFactory {

	StateSender create(Player p);
}
