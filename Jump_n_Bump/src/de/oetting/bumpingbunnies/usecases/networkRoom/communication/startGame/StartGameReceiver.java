package de.oetting.bumpingbunnies.usecases.networkRoom.communication.startGame;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.ConnectionToServerService;

public class StartGameReceiver extends MessageReceiverTemplate<String> {

	private final ConnectionToServerService service;

	public StartGameReceiver(NetworkToGameDispatcher dispatcher, ConnectionToServerService service) {
		super(dispatcher, MessageIds.START_GAME_ID, String.class);
		this.service = service;
	}

	@Override
	public void onReceiveMessage(String message) {
		this.service.onReceiveStartGame();
	}

}
