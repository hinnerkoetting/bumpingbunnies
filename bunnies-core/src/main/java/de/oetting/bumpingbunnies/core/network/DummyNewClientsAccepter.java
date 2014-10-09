package de.oetting.bumpingbunnies.core.network;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;

public class DummyNewClientsAccepter implements NewClientsAccepter {

	private static final Logger LOGGER = LoggerFactory.getLogger(DummyNewClientsAccepter.class);

	@Override
	public void start() {
		LOGGER.info("Noop start");
	}

	@Override
	public void cancel() {
		LOGGER.info("Noop cancel");
	}

	@Override
	public void clientConnectedSucessfull(MySocket socket) {
		LOGGER.info("Noop connect");
	}

	@Override
	public void addPlayerEntry(MySocket socket, PlayerProperties playerProperties, int socketIndex) {
		LOGGER.info("Noop add player");
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
	public void setMain(PlayerJoinListener main) {
	}

}
