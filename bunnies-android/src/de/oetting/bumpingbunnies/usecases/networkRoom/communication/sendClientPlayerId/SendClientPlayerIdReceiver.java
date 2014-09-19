package de.oetting.bumpingbunnies.usecases.networkRoom.communication.sendClientPlayerId;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.ConnectionToServerService;

public class SendClientPlayerIdReceiver extends MessageReceiverTemplate<Integer> {

	private final ConnectionToServerService service;

	public SendClientPlayerIdReceiver(NetworkToGameDispatcher dispatcher,
			ConnectionToServerService service) {
		super(dispatcher, MessageId.SEND_CLIENT_PLAYER_ID, Integer.class);
		this.service = service;
	}

	@Override
	public void onReceiveMessage(Integer object) {
		this.service.addMyPlayerRoomEntry(object);
	}

}
