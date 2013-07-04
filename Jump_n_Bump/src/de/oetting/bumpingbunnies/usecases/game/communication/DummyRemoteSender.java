package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.usecases.game.communication.objects.JsonWrapper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;
import de.oetting.bumpingbunnies.usecases.start.communication.MySocket;

public class DummyRemoteSender implements RemoteSender {

	@Override
	public void sendPlayerCoordinates(Player player) {
	}

	@Override
	public void cancel() {
	}

	@Override
	public void sendPlayerCoordinates(PlayerState playerState) {
	}

	@Override
	public boolean usesThisSocket(MySocket socket) {
		return false;
	}

	@Override
	public void sendMessage(int id, Object message) {
	}

	@Override
	public void sendMessage(JsonWrapper wrapper) {
	}

}
