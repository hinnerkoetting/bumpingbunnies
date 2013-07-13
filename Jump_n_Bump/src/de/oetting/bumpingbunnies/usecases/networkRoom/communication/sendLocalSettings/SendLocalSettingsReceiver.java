package de.oetting.bumpingbunnies.usecases.networkRoom.communication.sendLocalSettings;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalPlayersettings;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.ConnectionToClientService;

public class SendLocalSettingsReceiver extends MessageReceiverTemplate<LocalPlayersettings> {

	private final ConnectionToClientService service;

	public SendLocalSettingsReceiver(NetworkToGameDispatcher dispatcher, ConnectionToClientService service) {
		super(dispatcher, MessageId.SEND_CLIENT_LOCAL_PLAYER_SETTINGS, LocalPlayersettings.class);
		this.service = service;
	}

	@Override
	public void onReceiveMessage(LocalPlayersettings object) {
		this.service.onReceiveLocalPlayersettings(object);
	}
}
