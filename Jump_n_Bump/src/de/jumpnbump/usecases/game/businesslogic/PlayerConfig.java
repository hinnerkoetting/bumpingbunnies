package de.jumpnbump.usecases.game.businesslogic;

import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.configuration.OtherPlayerConfiguration;
import de.jumpnbump.usecases.game.factories.AbstractInputServiceFactory;
import de.jumpnbump.usecases.game.factories.AbstractOtherPlayersFactory;
import de.jumpnbump.usecases.game.model.World;

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
