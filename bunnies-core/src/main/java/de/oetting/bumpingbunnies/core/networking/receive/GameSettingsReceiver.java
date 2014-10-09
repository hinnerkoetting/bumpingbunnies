package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.client.SetupConnectionWithServer;
import de.oetting.bumpingbunnies.model.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.model.network.MessageId;

public class GameSettingsReceiver extends MessageReceiverTemplate<GeneralSettings> {

	private final SetupConnectionWithServer service;

	public GameSettingsReceiver(NetworkToGameDispatcher dispatcher, SetupConnectionWithServer service) {
		super(dispatcher, MessageId.SEND_CONFIGURATION_ID, GeneralSettings.class);
		this.service = service;
	}

	@Override
	public void onReceiveMessage(GeneralSettings object) {
		this.service.onReceiveGameSettings(object);
	}

}
