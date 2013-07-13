package de.oetting.bumpingbunnies.usecases.game.communication.messages.stop;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;

public class StopGameReceiver extends MessageReceiverTemplate<String> {

	private final GameActivity gameActivity;

	public StopGameReceiver(NetworkToGameDispatcher dispatcher, GameActivity gameActivity) {
		super(dispatcher, MessageIds.STOP_GAME, String.class);
		this.gameActivity = gameActivity;
	}

	@Override
	public void onReceiveMessage(String object) {
		this.gameActivity.receiveStopGame();
	}

}
