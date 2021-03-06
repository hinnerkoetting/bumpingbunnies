package de.oetting.bumpingbunnies.core.input.factory;

import de.oetting.bumpingbunnies.core.configuration.OpponentInputFactory;
import de.oetting.bumpingbunnies.core.input.OpponentInput;
import de.oetting.bumpingbunnies.core.input.ai.AiInputService;
import de.oetting.bumpingbunnies.core.input.ai.DummyInputService;
import de.oetting.bumpingbunnies.core.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.core.networking.messaging.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.world.World;

public class OpponentInputFactoryImpl implements OpponentInputFactory {

	private final World world;
	private final PlayerStateDispatcher stateDispatcher;
	private final Configuration configuration;

	public OpponentInputFactoryImpl(World world, PlayerStateDispatcher stateDispatcher, Configuration configuration) {
		this.world = world;
		this.stateDispatcher = stateDispatcher;
		this.configuration = configuration;
	}

	@Override
	public OpponentInput create(Bunny player) {
		if (player.getOpponent().isLocalHumanPlayer()) {
			return new DummyInputService();
		} else if (!player.getOpponent().isLocalPlayer()) {
			PlayerFromNetworkInput input = new PlayerFromNetworkInput(player, configuration.isHost());
			this.stateDispatcher.addInputService(player.id(), input);
			return input;
		} else {
			return new AiInputService(player, this.world);
		}
	}
}
