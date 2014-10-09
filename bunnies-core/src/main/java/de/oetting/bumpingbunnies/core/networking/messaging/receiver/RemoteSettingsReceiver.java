package de.oetting.bumpingbunnies.core.networking.messaging.receiver;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.server.ToClientConnector;
import de.oetting.bumpingbunnies.model.configuration.RemoteSettings;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class RemoteSettingsReceiver extends MessageReceiverTemplate<RemoteSettings> {

	private final ToClientConnector service;

	public RemoteSettingsReceiver(NetworkToGameDispatcher dispatcher, ToClientConnector service) {
		super(dispatcher, MessageId.CLIENT_REMOTE_SETTINGS, RemoteSettings.class);
		this.service = service;
	}

	@Override
	public void onReceiveMessage(RemoteSettings object) {
		this.service.onReceiveRemotePlayersettings(object);
	}
}
