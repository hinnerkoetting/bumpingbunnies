package de.oetting.bumpingbunnies.usecases.networkRoom.communication.startGame;

import de.oetting.bumpingbunnies.core.networking.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.ConnectionToServerService;

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
