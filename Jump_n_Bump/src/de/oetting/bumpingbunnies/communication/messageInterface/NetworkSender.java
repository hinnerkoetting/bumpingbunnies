package de.oetting.bumpingbunnies.communication.messageInterface;

import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;

public interface NetworkSender {
	void sendMessage(JsonWrapper message);
}
