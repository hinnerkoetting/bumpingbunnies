package de.oetting.bumpingbunnies.usecases.game.communication;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;

public class DummyNewClientsAccepter implements NewClientsAccepter {

	@Override
	public void start() {
	}

	@Override
	public void cancel() {
	}

	@Override
	public void clientConnectedSucessfull(MySocket socket) {
	}

	@Override
	public void addPlayerEntry(MySocket socket, PlayerProperties playerProperties, int socketIndex) {
	}

	@Override
	public int getNextPlayerId() {
		return -1;
	}

	@Override
	public List<PlayerProperties> getAllPlayersProperties() {
		return new ArrayList<PlayerProperties>();
	}

	@Override
	public List<MySocket> getAllOtherSockets() {
		return new ArrayList<MySocket>();
	}

	@Override
	public void setMain(GameMain main) {
	}

}
