package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.networking.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.client.ConnectionToServerService;
import de.oetting.bumpingbunnies.model.networking.MessageId;

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
