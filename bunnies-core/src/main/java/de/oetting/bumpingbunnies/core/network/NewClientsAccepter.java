package de.oetting.bumpingbunnies.core.network;

import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;

public interface NewClientsAccepter extends AcceptsClientConnections {

	void start();

	void cancel();

	void setMain(PlayerJoinListener main);
}
