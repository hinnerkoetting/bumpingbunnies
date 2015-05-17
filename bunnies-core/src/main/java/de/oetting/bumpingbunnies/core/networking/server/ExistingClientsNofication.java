package de.oetting.bumpingbunnies.core.networking.server;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.sender.OtherPlayerClientIdSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSender;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;

public class ExistingClientsNofication {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExistingClientsNofication.class);

	private final SocketStorage sockets;
	private final PlayerDisconnectedCallback disconnectCallback;

	public ExistingClientsNofication(SocketStorage sockets, PlayerDisconnectedCallback disconnectCallback) {
		this.sockets = sockets;
		this.disconnectCallback = disconnectCallback;
	}

	public void informAboutNewBunny(PlayerProperties playerProperties) {
		LOGGER.info("Notifying existing clients about new player with id %d", playerProperties.getPlayerId());
		synchronized (sockets) {
			List<MySocket> allOtherPlayers = new ArrayList<MySocket>(this.sockets.getAllSockets());
			for (MySocket otherPlayer : allOtherPlayers) {
				SimpleNetworkSender networkSender = SimpleNetworkSenderFactory.createNetworkSender(otherPlayer,
						disconnectCallback);
				new OtherPlayerClientIdSender(networkSender).sendMessage(playerProperties);
			}
		}
	}
}
