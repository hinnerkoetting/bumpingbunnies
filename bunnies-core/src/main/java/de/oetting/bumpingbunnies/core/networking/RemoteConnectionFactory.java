package de.oetting.bumpingbunnies.core.networking;

import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.factory.OpponentTypeSendFactory;
import de.oetting.bumpingbunnies.core.networking.factory.OpponentTypeSendFactoryFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class RemoteConnectionFactory {

	private final GameStopper stopper;
	private final SocketStorage sockets;

	public RemoteConnectionFactory(GameStopper stopper, SocketStorage sockets) {
		super();
		this.stopper = stopper;
		this.sockets = sockets;
	}

	public NetworkSender create(Player player) {
		OpponentTypeSendFactory sendFactory = new OpponentTypeSendFactoryFactory().createSendFactory(player.getOpponent().getType());
		return sendFactory.createNetworkSender(player, this.stopper, this.sockets);
	}

}
