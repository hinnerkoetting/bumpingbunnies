package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import com.google.gson.Gson;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.communication.SimpleNetworkSender;

public class SimpleNetworkSenderFactory {

	public static SimpleNetworkSender createNetworkSender(MySocket socket) {
		return new SimpleNetworkSender(new Gson(), socket);
	}
}
