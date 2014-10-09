package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.client.SetupConnectionWithServer;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class SendClientPlayerIdReceiver extends MessageReceiverTemplate<Integer> {

	private final SetupConnectionWithServer service;

	public SendClientPlayerIdReceiver(NetworkToGameDispatcher dispatcher,
			SetupConnectionWithServer service) {
		super(dispatcher, MessageId.CLIENT_PLAYER_ID, Integer.class);
		this.service = service;
	}

	@Override
	public void onReceiveMessage(Integer object) {
		this.service.addMyPlayerRoomEntry(object);
	}

}
