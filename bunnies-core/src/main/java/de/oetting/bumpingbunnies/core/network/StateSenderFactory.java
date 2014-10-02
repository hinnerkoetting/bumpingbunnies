package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface StateSenderFactory {

	StateSender create(Player p);
}
