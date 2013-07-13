package de.oetting.bumpingbunnies.usecases.networkRoom.communication.otherPlayerId;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.ConnectionToServerService;

public class OtherPlayerClientIdReceiver extends MessageReceiverTemplate<PlayerProperties> {

	private final ConnectionToServerService service;

	public OtherPlayerClientIdReceiver(NetworkToGameDispatcher dispatcher, ConnectionToServerService service) {
		super(dispatcher, MessageIds.SEND_OTHER_PLAYER_ID, PlayerProperties.class);
		this.service = service;
	}

	@Override
	public void onReceiveMessage(PlayerProperties object) {
		this.service.addOtherPlayer(object);
	}

}
