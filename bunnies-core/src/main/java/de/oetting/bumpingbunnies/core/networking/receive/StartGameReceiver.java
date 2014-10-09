package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.client.SetupConnectionWithServer;
import de.oetting.bumpingbunnies.core.networking.messaging.StartGameMessage;

public class StartGameReceiver extends MessageReceiverTemplate<String> {

	private final SetupConnectionWithServer service;

	public StartGameReceiver(NetworkToGameDispatcher dispatcher, SetupConnectionWithServer service) {
		super(dispatcher, new StartGameMessage());
		this.service = service;
	}

	@Override
	public void onReceiveMessage(String message) {
		this.service.onReceiveStartGame();
	}

}
