package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

public interface NetworkListener {

	void newMessage(JsonWrapper wrapper);
}
