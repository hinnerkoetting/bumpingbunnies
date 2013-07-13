package de.oetting.bumpingbunnies.usecases.game.communication.objects;

/**
 * Identifies different types of messagges send through network.
 */
public enum MessageId {
	START_GAME_ID, SEND_CONFIGURATION_ID, SEND_CLIENT_PLAYER_ID, SEND_OTHER_PLAYER_ID, SEND_CLIENT_LOCAL_PLAYER_SETTINGS, STOP_GAME, PLAYER_IS_DEAD_MESSAGE, SEND_PLAYER_STATE;

	private Class<?> clazz;

}
