package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.communication.IncomingNetworkDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractInputServiceFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractOtherPlayersFactory;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class PlayerConfig {

	private final OpponentConfiguration configuration;
	private final AbstractOtherPlayersFactory otherPlayerFactory;
	private final PlayerMovementController movementController;
	private final World world;

	public PlayerConfig(AbstractOtherPlayersFactory otherPlayerFactory,
			PlayerMovementController movementController, World world,
			OpponentConfiguration configuration) {
		super();
		this.otherPlayerFactory = otherPlayerFactory;
		this.movementController = movementController;
		this.world = world;
		this.configuration = configuration;
	}

	public PlayerMovementController getMovementController() {
		return this.movementController;
	}

	public InputService createInputService(List<RemoteSender> allSender,
			IncomingNetworkDispatcher networkToGameDispatcher) {
		AbstractInputServiceFactory inputServiceFactory = this.otherPlayerFactory
				.getInputServiceFactory();
		return inputServiceFactory.create(this.movementController, this.world);
	}

	public AbstractOtherPlayersFactory getOtherPlayerFactory() {
		return this.otherPlayerFactory;
	}

	public OpponentConfiguration getConfiguration() {
		return this.configuration;
	}

}
