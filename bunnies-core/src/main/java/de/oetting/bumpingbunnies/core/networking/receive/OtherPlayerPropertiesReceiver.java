package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class OtherPlayerPropertiesReceiver extends MessageReceiverTemplate<PlayerProperties> {

	private final PlayerPropertiesReceiveListener service;

	public OtherPlayerPropertiesReceiver(NetworkToGameDispatcher dispatcher, PlayerPropertiesReceiveListener service) {
		super(dispatcher, MessageId.OTHER_PLAYER_PROPERTIES, PlayerProperties.class);
		this.service = service;
	}

	@Override
	public void onReceiveMessage(PlayerProperties object) {
		this.service.addOtherPlayer(object);
	}

}
