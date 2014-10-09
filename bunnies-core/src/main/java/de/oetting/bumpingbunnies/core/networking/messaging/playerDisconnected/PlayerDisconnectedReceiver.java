package de.oetting.bumpingbunnies.core.networking.messaging.playerDisconnected;

import de.oetting.bumpingbunnies.core.network.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.core.network.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;

public class PlayerDisconnectedReceiver extends MessageReceiverTemplate<PlayerDisconnectedMessage> {

	private final PlayerDisconnectedCallback callback;

	public PlayerDisconnectedReceiver(NetworkToGameDispatcher dispatcher, PlayerDisconnectedCallback callback) {
		super(dispatcher, new PlayerDisconnectedMetadata());
		this.callback = callback;
	}

	@Override
	public void onReceiveMessage(PlayerDisconnectedMessage object) {
		callback.playerDisconnected(object.getOpponent());
	}
}
