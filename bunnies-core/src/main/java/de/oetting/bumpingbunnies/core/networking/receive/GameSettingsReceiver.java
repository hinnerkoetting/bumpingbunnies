package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.client.SetupConnectionWithServer;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class GameSettingsReceiver extends MessageReceiverTemplate<ServerSettings> {

	private final SetupConnectionWithServer service;

	public GameSettingsReceiver(NetworkToGameDispatcher dispatcher, SetupConnectionWithServer service) {
		super(dispatcher, MessageId.SERVER_SETTINGS_ID, ServerSettings.class);
		this.service = service;
	}

	@Override
	public void onReceiveMessage(ServerSettings object) {
		this.service.onReceiveGameSettings(object);
	}

}
