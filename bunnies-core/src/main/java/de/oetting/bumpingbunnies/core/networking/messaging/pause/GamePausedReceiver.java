package de.oetting.bumpingbunnies.core.networking.messaging.pause;

import de.oetting.bumpingbunnies.core.game.main.GameMain;
import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;

public class GamePausedReceiver extends MessageReceiverTemplate<Boolean> {

	private final GameMain main;

	public GamePausedReceiver(NetworkToGameDispatcher dispatcher, GameMain main) {
		super(dispatcher, new GamePausedMetadata());
		this.main = main;
	}

	@Override
	public void onReceiveMessage(Boolean object) {
		main.pause(object);
	}
}
