package de.oetting.bumpingbunnies.model.network;

/**
 * Identifies different types of messagges send through network.
 */
public enum MessageId {
	START_GAME_ID, //
	SERVER_SETTINGS_ID, //
	CLIENT_PLAYER_ID, //
	OTHER_PLAYER_PROPERTIES, //
	CLIENT_REMOTE_SETTINGS, //
	STOP_GAME, //
	PLAYER_IS_DEAD_MESSAGE, //
	SEND_PLAYER_STATE, //
	PLAYER_IS_REVIVED, //
	PLAYER_SCORE_UPDATE, //
	SPAWN_POINT, //
	PLAYER_DISCONNECTED,//
	BUNNY_DEAD_RECEIVED

}
