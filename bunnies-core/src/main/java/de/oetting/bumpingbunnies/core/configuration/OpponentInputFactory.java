package de.oetting.bumpingbunnies.core.configuration;

import de.oetting.bumpingbunnies.core.input.OpponentInput;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface OpponentInputFactory {

	OpponentInput create(Player p);
}
