package de.oetting.bumpingbunnies.communication;

import java.io.IOException;

import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public interface MySocket {

	void close();

	void connect();

	void sendMessage(String message);

	String blockingReceive();

	boolean isFastSocketPossible();

	Opponent getOwner();

}
