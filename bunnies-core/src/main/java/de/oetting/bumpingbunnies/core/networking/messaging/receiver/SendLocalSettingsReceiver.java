package de.oetting.bumpingbunnies.core.networking.messaging.receiver;

import de.oetting.bumpingbunnies.core.networking.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.server.ConnectionToClientService;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalPlayerSettings;

public class SendLocalSettingsReceiver extends MessageReceiverTemplate<LocalPlayerSettings> {

	private final ConnectionToClientService service;

	public SendLocalSettingsReceiver(NetworkToGameDispatcher dispatcher, ConnectionToClientService service) {
		super(dispatcher, MessageId.SEND_CLIENT_LOCAL_PLAYER_SETTINGS, LocalPlayerSettings.class);
		this.service = service;
	}

	@Override
	public void onReceiveMessage(LocalPlayerSettings object) {
		this.service.onReceiveLocalPlayersettings(object);
	}
}
