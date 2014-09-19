package de.oetting.bumpingbunnies.usecases.networkRoom;

import java.util.List;

import de.oetting.bumpingbunnies.core.networking.MySocket;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;

public interface AcceptsClientConnections {

	void clientConnectedSucessfull(final MySocket socket);

	void addPlayerEntry(MySocket socket, PlayerProperties playerProperties, int socketIndex);

	int getNextPlayerId();

	List<PlayerProperties> getAllPlayersProperties();

	List<MySocket> getAllOtherSockets();

}
