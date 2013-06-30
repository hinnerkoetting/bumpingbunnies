package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.communication.InformationSupplier;
import de.oetting.bumpingbunnies.usecases.game.configuration.OtherPlayerConfiguration;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractInputServiceFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractOtherPlayersFactory;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class PlayerConfig {

	private final OtherPlayerConfiguration configuration;
	private final AbstractOtherPlayersFactory otherPlayerFactory;
	private final PlayerMovementController movementController;
	private final World world;

	public PlayerConfig(AbstractOtherPlayersFactory otherPlayerFactory,
			PlayerMovementController movementController, World world,
			OtherPlayerConfiguration configuration) {
		super();
		this.otherPlayerFactory = otherPlayerFactory;
		this.movementController = movementController;
		this.world = world;
		this.configuration = configuration;
	}

	public PlayerMovementController getMovementController() {
		return this.movementController;
	}

	public InputService createInputService() {
		InformationSupplier informationSupplier = this.otherPlayerFactory
				.createInformationSupplier();
		AbstractInputServiceFactory inputServiceFactory = this.otherPlayerFactory
				.getInputServiceFactory();
		return inputServiceFactory.create(informationSupplier,
				this.movementController, this.world);
	}

	public AbstractOtherPlayersFactory getOtherPlayerFactory() {
		return this.otherPlayerFactory;
	}

	public OtherPlayerConfiguration getConfiguration() {
		return this.configuration;
	}

}
