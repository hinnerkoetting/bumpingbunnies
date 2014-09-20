package de.oetting.bumpingbunnies.core.networking;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface StateSenderFactory {

	StateSender create(Player p);
}
