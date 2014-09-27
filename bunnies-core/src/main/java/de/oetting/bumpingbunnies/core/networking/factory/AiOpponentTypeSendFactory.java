package de.oetting.bumpingbunnies.core.networking.factory;

import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.DummyRemoteSender;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class AiOpponentTypeSendFactory implements OpponentTypeSendFactory {

	@Override
	public NetworkSender createNetworkSender(Player player, GameStopper activity, SocketStorage sockets) {
		return new DummyRemoteSender(player.getOpponent());
	}

}