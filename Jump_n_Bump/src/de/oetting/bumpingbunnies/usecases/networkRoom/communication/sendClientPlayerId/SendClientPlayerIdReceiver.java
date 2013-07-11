package de.oetting.bumpingbunnies.usecases.networkRoom.communication.sendClientPlayerId;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.ConnectionToServerService;

public class SendClientPlayerIdReceiver extends MessageReceiverTemplate<Integer> {

	private final ConnectionToServerService service;

	public SendClientPlayerIdReceiver(NetworkToGameDispatcher dispatcher,
			ConnectionToServerService service) {
		super(dispatcher, MessageIds.SEND_CLIENT_PLAYER_ID, Integer.class);
		this.service = service;
	}

	@Override
	public void onReceiveMessage(Integer object) {
		this.service.addMyPlayerRoomEntry(object);
	}

}
