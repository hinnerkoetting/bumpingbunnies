package de.oetting.bumpingbunnies.usecases.game.communication.objects;

import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead.PlayerIsDead;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalPlayersettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

/**
 * Identifies different types of messagges send through network.
 */
public enum MessageId {
	START_GAME_ID(String.class), SEND_CONFIGURATION_ID(GeneralSettings.class), SEND_CLIENT_PLAYER_ID(Integer.class), SEND_OTHER_PLAYER_ID(
			PlayerProperties.class), SEND_CLIENT_LOCAL_PLAYER_SETTINGS(LocalPlayersettings.class), STOP_GAME(String.class), PLAYER_IS_DEAD_MESSAGE(
			PlayerIsDead.class), SEND_PLAYER_STATE(PlayerState.class);

	private final Class<?> clazz;

	private <T> MessageId(Class<T> clazz) {
		this.clazz = clazz;
	}

	public <T> Class<T> getClazz() {
		return (Class<T>) this.clazz;
	}

}
