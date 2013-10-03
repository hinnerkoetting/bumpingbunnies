package de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsRevived;

import de.oetting.bumpingbunnies.communication.messageInterface.MessageReceiverTemplate;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerSearcher;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class PlayerIsRevivedReceiver extends MessageReceiverTemplate<Integer> {
	private final World world;

	public PlayerIsRevivedReceiver(NetworkToGameDispatcher dispatcher, World world) {
		super(dispatcher, new PlayerIsRevivedMetadata());
		this.world = world;
	}

	@Override
	public void onReceiveMessage(Integer object) {
		Player player = PlayerSearcher.findPlayer(this.world.getAllPlayer(), object);
		player.setDead(false);
	}

}
