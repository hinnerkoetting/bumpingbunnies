package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import java.util.List;

import de.oetting.bumpingbunnies.core.networking.NetworkMessageDistributor;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.receiver.OpponentTypeReceiveFactory;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiverFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class NetworkReceiveThreadFactory implements NetworkReceiverFactory {

	private final SocketStorage sockets;
	private final NetworkToGameDispatcher networkDispatcher;
	private final NetworkMessageDistributor sendControl;

	public NetworkReceiveThreadFactory(SocketStorage sockets, NetworkToGameDispatcher networkDispatcher, NetworkMessageDistributor sendControl) {
		super();
		this.sockets = sockets;
		this.sendControl = sendControl;
		this.networkDispatcher = networkDispatcher;
	}

	@Override
	public List<NetworkReceiver> create(Player player) {
		OpponentTypeFactory factory = new OpponentTypeReceiveFactoryFactory().createReceiveFactory(player.getOpponent().getType());
		OpponentTypeReceiveFactory receiveFactory = factory.createReceiveFactory();
		return receiveFactory.createReceiveThreadsForOnePlayer(this.sockets, player, this.networkDispatcher, this.sendControl);
	}

}
