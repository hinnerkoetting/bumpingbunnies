package de.oetting.bumpingbunnies.core.networking.receive;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.client.ConnectionToServerService;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;

public class GameSettingsReceiver extends MessageReceiverTemplate<GeneralSettings> {

	private final ConnectionToServerService service;

	public GameSettingsReceiver(NetworkToGameDispatcher dispatcher, ConnectionToServerService service) {
		super(dispatcher, MessageId.SEND_CONFIGURATION_ID, GeneralSettings.class);
		this.service = service;
	}

	@Override
	public void onReceiveMessage(GeneralSettings object) {
		this.service.onReceiveGameSettings(object);
	}

}
