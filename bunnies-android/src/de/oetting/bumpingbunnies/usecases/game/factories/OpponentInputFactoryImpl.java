package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.core.configuration.OpponentInputFactory;
import de.oetting.bumpingbunnies.core.game.movement.PlayerMovement;
import de.oetting.bumpingbunnies.core.input.OpponentInput;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.android.input.ai.AiInputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.ai.DummyInputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class OpponentInputFactoryImpl implements OpponentInputFactory {

	private final World world;
	private final PlayerStateDispatcher stateDispatcher;

	public OpponentInputFactoryImpl(World world, PlayerStateDispatcher stateDispatcher) {
		this.world = world;
		this.stateDispatcher = stateDispatcher;
	}

	@Override
	public OpponentInput create(Player p) {
		if (p.getOpponent().isMyPlayer()) {
			return new DummyInputService();
		} else if (!p.getOpponent().isLocalPlayer()) {
			PlayerFromNetworkInput input = new PlayerFromNetworkInput(p);
			this.stateDispatcher.addInputService(p.id(), input);
			return input;
		} else {
			return new AiInputService(new PlayerMovement(p), this.world);
		}
	}
}
