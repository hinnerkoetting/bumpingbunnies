package de.oetting.bumpingbunnies.core.networking;

import de.oetting.bumpingbunnies.model.networking.JsonWrapper;

public interface NetworkListener {

	void newMessage(JsonWrapper wrapper);
}
