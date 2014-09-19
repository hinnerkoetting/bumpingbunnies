package de.oetting.bumpingbunnies.usecases.game.communication;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.networkRoom.AcceptsClientConnections;

public interface NewClientsAccepter extends AcceptsClientConnections {

	void start();

	void cancel();

	void setMain(GameMain main);
}
