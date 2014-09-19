package de.oetting.bumpingbunnies.usecases.networkRoom.communication.otherPlayerId;

import de.oetting.bumpingbunnies.core.networking.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.ConnectionToServerService;

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
