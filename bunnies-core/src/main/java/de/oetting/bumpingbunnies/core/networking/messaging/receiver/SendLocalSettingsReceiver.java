package de.oetting.bumpingbunnies.core.networking.messaging.receiver;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.server.ConnectionToClientService;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.network.MessageId;

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
