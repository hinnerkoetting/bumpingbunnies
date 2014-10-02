package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.client.ConnectionToServerService;
import de.oetting.bumpingbunnies.core.networking.messaging.StartGameMessage;

public class StartGameReceiver extends MessageReceiverTemplate<String> {

	private final ConnectionToServerService service;

	public StartGameReceiver(NetworkToGameDispatcher dispatcher, ConnectionToServerService service) {
		super(dispatcher, new StartGameMessage());
		this.service = service;
	}

	@Override
	public void onReceiveMessage(String message) {
		this.service.onReceiveStartGame();
	}

}
