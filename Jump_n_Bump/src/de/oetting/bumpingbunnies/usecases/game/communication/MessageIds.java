package de.oetting.bumpingbunnies.usecases.game.communication;

public class MessageIds {

	public static final int START_GAME_ID = 100000;
	public static final int SEND_CONFIGURATION_ID = 100001;
	public static final int SEND_CLIENT_PLAYER_ID = 100002;
	public static final int SEND_OTHER_PLAYER_ID = 100003;
	public static final int SEND_CLIENT_LOCAL_PLAYER_SETTINGS = 100004;
	public static final int STOP_GAME = 100005;
	public static final int PLAYER_IS_DEAD_MESSAGE = 100006;

	private MessageIds() {
	}
}
