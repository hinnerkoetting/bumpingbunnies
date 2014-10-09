package de.oetting.bumpingbunnies.core.networking.server;

import java.util.List;

import de.oetting.bumpingbunnies.core.network.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;

public class AcceptsClientConnectionsDelegate implements AcceptsClientConnections {

	private AcceptsClientConnections accepter;

	@Override
	public void clientConnectedSucessfull(MySocket socket) {
		this.accepter.clientConnectedSucessfull(socket);
	}

	@Override
	public void addPlayerEntry(MySocket socket, PlayerProperties playerProperties, int socketIndex) {
		this.accepter.addPlayerEntry(socket, playerProperties, socketIndex);
	}

	@Override
	public int getNextPlayerId() {
		return this.accepter.getNextPlayerId();
	}

	public void setAccepter(AcceptsClientConnections accepter) {
		this.accepter = accepter;
	}

	@Override
	public List<PlayerProperties> getAllPlayersProperties() {
		return this.accepter.getAllPlayersProperties();
	}

}
