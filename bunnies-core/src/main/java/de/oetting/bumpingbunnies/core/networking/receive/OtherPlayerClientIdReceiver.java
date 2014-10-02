package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.client.ConnectionToServerService;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class OtherPlayerClientIdReceiver extends MessageReceiverTemplate<PlayerProperties> {

	private final ConnectionToServerService service;

	public OtherPlayerClientIdReceiver(NetworkToGameDispatcher dispatcher, ConnectionToServerService service) {
		super(dispatcher, MessageId.SEND_OTHER_PLAYER_ID, PlayerProperties.class);
		this.service = service;
	}

	@Override
	public void onReceiveMessage(PlayerProperties object) {
		this.service.addOtherPlayer(object);
	}

}
