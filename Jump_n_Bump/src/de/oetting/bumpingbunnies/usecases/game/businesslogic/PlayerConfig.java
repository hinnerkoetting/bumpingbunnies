package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.usecases.game.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.usecases.game.factories.OtherPlayerInputServiceFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractOtherPlayersFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.OtherPlayerInputService;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class PlayerConfig {

	private final OpponentConfiguration configuration;
	private final AbstractOtherPlayersFactory otherPlayerFactory;
	private final PlayerMovement movementController;
	private final World world;

	public PlayerConfig(AbstractOtherPlayersFactory otherPlayerFactory,
			PlayerMovement movementController, World world,
			OpponentConfiguration configuration) {
		super();
		this.otherPlayerFactory = otherPlayerFactory;
		this.movementController = movementController;
		this.world = world;
		this.configuration = configuration;
	}

	public PlayerMovement getMovementController() {
		return this.movementController;
	}

	public OtherPlayerInputService createInputService() {
		OtherPlayerInputServiceFactory inputServiceFactory = this.otherPlayerFactory
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
