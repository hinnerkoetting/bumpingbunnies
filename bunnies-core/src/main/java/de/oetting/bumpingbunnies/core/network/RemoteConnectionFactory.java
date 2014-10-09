package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.factory.OpponentTypeSendFactory;
import de.oetting.bumpingbunnies.core.networking.factory.OpponentTypeSendFactoryFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class RemoteConnectionFactory {

	private final GameStopper stopper;
	private final SocketStorage sockets;
	private final PlayerDisconnectedCallback disconnectCallback;

	public RemoteConnectionFactory(GameStopper stopper, SocketStorage sockets, PlayerDisconnectedCallback disconnectCallback) {
		this.stopper = stopper;
		this.sockets = sockets;
		this.disconnectCallback = disconnectCallback;
	}

	public NetworkSender create(Player player) {
		OpponentTypeSendFactory sendFactory = new OpponentTypeSendFactoryFactory().createSendFactory(player.getOpponent().getType());
		return sendFactory.createNetworkSender(player, this.stopper, this.sockets, disconnectCallback);
	}

}
