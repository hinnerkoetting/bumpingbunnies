package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import de.oetting.bumpingbunnies.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class RemoteConnectionFactory {

	private final GameActivity activity;
	private final SocketStorage sockets;

	public RemoteConnectionFactory(GameActivity activity, SocketStorage sockets) {
		super();
		this.activity = activity;
		this.sockets = sockets;
	}

	public NetworkSender create(Player player) {
		OpponentTypeFactory factory = player.getOpponent().getType().getFactory();
		OpponentTypeSendFactory sendFactory = factory.createSendFactory();
		return sendFactory.createNetworkSender(player, this.activity, this.sockets);
	}

}