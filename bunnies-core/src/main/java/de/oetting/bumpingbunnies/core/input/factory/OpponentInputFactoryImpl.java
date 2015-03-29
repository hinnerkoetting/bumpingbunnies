package de.oetting.bumpingbunnies.core.input.factory;

import de.oetting.bumpingbunnies.core.configuration.OpponentInputFactory;
import de.oetting.bumpingbunnies.core.input.OpponentInput;
import de.oetting.bumpingbunnies.core.input.ai.AiInputService;
import de.oetting.bumpingbunnies.core.input.ai.DummyInputService;
import de.oetting.bumpingbunnies.core.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class OpponentInputFactoryImpl implements OpponentInputFactory {

	private final World world;
	private final PlayerStateDispatcher stateDispatcher;

	public OpponentInputFactoryImpl(World world, PlayerStateDispatcher stateDispatcher) {
		this.world = world;
		this.stateDispatcher = stateDispatcher;
	}

	@Override
	public OpponentInput create(Player player) {
		if (player.getOpponent().isLocalHumanPlayer()) {
			return new DummyInputService();
		} else if (!player.getOpponent().isLocalPlayer()) {
			PlayerFromNetworkInput input = new PlayerFromNetworkInput(player);
			this.stateDispatcher.addInputService(player.id(), input);
			return input;
		} else {
			return new AiInputService(player, this.world);
		}
	}
}
