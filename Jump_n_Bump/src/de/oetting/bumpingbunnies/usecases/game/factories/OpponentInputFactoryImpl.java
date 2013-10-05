package de.oetting.bumpingbunnies.usecases.game.factories;

import de.oetting.bumpingbunnies.usecases.game.android.input.ai.AiInputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.ai.DummyInputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameMain;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerMovement;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class OpponentInputFactoryImpl implements OpponentInputFactory {

	private final GameMain main;
	private final World world;
	private final PlayerStateDispatcher stateDispatcher;

	public OpponentInputFactoryImpl(GameMain main, World world, PlayerStateDispatcher stateDispatcher) {
		this.main = main;
		this.world = world;
		this.stateDispatcher = stateDispatcher;
	}

	@Override
	public OpponentInput create(Player p) {
		if (p.getOpponent().isMyPlayer()) {
			return new DummyInputService();
		} else if (this.main.existsRemoteConnection(p.getOpponent())) {
			PlayerFromNetworkInput input = new PlayerFromNetworkInput(p);
			this.stateDispatcher.addInputService(p.id(), input);
			return input;
		} else {
			return new AiInputService(new PlayerMovement(p), this.world);
		}
	}
}
