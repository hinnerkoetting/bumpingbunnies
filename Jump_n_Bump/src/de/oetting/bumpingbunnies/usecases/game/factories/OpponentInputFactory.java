package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface OpponentInputFactory {

	OpponentInput create(Player p);
}
