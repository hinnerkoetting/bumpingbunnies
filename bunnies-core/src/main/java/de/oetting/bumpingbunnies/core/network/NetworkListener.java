package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.model.network.JsonWrapper;

public interface NetworkListener {

	void newMessage(JsonWrapper wrapper);
}
