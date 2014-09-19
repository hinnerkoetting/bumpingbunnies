package de.oetting.bumpingbunnies.usecases.networkRoom.communication.generalSettings;

import de.oetting.bumpingbunnies.core.networking.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.networking.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.model.networking.MessageId;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.ConnectionToServerService;

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
