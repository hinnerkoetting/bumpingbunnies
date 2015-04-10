package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public interface StateSenderFactory {

	StateSender create(Bunny p);
}
