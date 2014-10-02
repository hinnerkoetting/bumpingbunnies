package de.oetting.bumpingbunnies.core.networking.factory;

import de.oetting.bumpingbunnies.core.network.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public interface OpponentTypeSendFactory {

	NetworkSender createNetworkSender(Player player, GameStopper activity, SocketStorage sockets);
}
