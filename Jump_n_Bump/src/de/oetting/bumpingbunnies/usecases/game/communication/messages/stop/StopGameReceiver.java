package de.oetting.bumpingbunnies.usecases.game.communication.messages.stop;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.android.GameActivity;
import de.oetting.bumpingbunnies.usecases.game.communication.MessageIds;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;

public class StopGameReceiver extends MessageReceiverTemplate<String> {

	private GameActivity gameActivity;

	public StopGameReceiver(NetworkToGameDispatcher dispatcher) {
		super(dispatcher, MessageIds.STOP_GAME, String.class);
	}

	@Override
	public void onReceiveMessage(String object) {
		this.gameActivity.receiveStopGame();
	}

}
