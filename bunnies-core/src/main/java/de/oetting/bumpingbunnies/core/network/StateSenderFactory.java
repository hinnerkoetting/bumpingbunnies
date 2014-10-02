package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.model.game.objects.Player;

public interface StateSenderFactory {

	StateSender create(Player p);
}
