package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import java.util.List;

import de.oetting.bumpingbunnies.communication.NetworkSendControl;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiver;
import de.oetting.bumpingbunnies.core.networking.receive.NetworkReceiverFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class NetworkReceiveThreadFactory implements NetworkReceiverFactory {

	private final SocketStorage sockets;
	private final NetworkToGameDispatcher networkDispatcher;
	private final NetworkSendControl sendControl;

	public NetworkReceiveThreadFactory(SocketStorage sockets, NetworkToGameDispatcher networkDispatcher, NetworkSendControl sendControl) {
		super();
		this.sockets = sockets;
		this.sendControl = sendControl;
		this.networkDispatcher = networkDispatcher;
	}

	@Override
	public List<NetworkReceiver> create(Player player) {
		OpponentTypeFactory factory = new OpponentTypeFactoryFactory().createFactory(player.getOpponent().getType());
		OpponentTypeReceiveFactory receiveFactory = factory.createReceiveFactory();
		return receiveFactory.createReceiveThreadsForOnePlayer(this.sockets, player, this.networkDispatcher, this.sendControl);
	}

}
