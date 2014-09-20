package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.android.game.SocketStorage;
import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.DummyRemoteSender;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class MyPlayerOpponentTypeSendFactory implements OpponentTypeSendFactory {

	@Override
	public NetworkSender createNetworkSender(Player player, GameActivity activity, SocketStorage sockets) {
		return new DummyRemoteSender(player.getOpponent());
	}

}
