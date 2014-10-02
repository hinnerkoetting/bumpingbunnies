package de.oetting.bumpingbunnies.core.networking;

import java.util.List;

import de.oetting.bumpingbunnies.core.networking.messaging.receiver.OpponentTypeReceiveFactory;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiverFactory;
import de.oetting.bumpingbunnies.core.networking.receive.OpponentTypeFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class NetworkReceiveThreadFactory implements NetworkReceiverFactory {

	private final SocketStorage sockets;
	private final NetworkToGameDispatcher networkDispatcher;
	private final NetworkMessageDistributor sendControl;
	private final OpponentReceiverFactoryFactory opponenFactoryFactory;

	public NetworkReceiveThreadFactory(SocketStorage sockets, NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl,
			OpponentReceiverFactoryFactory opponenFactoryFactory) {
		this.sockets = sockets;
		this.sendControl = sendControl;
		this.networkDispatcher = networkDispatcher;
		this.opponenFactoryFactory = opponenFactoryFactory;
	}

	@Override
	public List<NetworkReceiver> create(Player player) {
		OpponentTypeFactory factory = opponenFactoryFactory.createReceiveFactory(player.getOpponent().getType());
		OpponentTypeReceiveFactory receiveFactory = factory.createReceiveFactory();
		return receiveFactory.createReceiveThreadsForOnePlayer(this.sockets, player, this.networkDispatcher, this.sendControl);
	}

}
